package com.kynetics.updatefactory.ddiclient.core.actors

import com.kynetics.updatefactory.ddiapiclient.api.model.*
import com.kynetics.updatefactory.ddiclient.core.actors.ConnectionManager.Companion.Message.In
import com.kynetics.updatefactory.ddiclient.core.actors.ConnectionManager.Companion.Message.Out.*
import com.kynetics.updatefactory.ddiclient.core.actors.ConnectionManager.Companion.Message.Out.Err.ErrMsg
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.runBlocking
import com.kynetics.updatefactory.ddiclient.core.actors.ActionManager.Companion.Message.*
import com.kynetics.updatefactory.ddiclient.core.actors.DeploymentManager.Companion.Message.*
import com.kynetics.updatefactory.ddiclient.core.api.MessageListener
import org.joda.time.Duration

@UseExperimental(ObsoleteCoroutinesApi::class)
class ActionManager
private constructor(scope: ActorScope): AbstractActor(scope) {

    private val registry = coroutineContext[UFClientContext]!!.registry
    private val configDataProvider = coroutineContext[UFClientContext]!!.configDataProvider
    private val connectionManager  = coroutineContext[CMActor]!!.ref
    private val notificationManager  = coroutineContext[NMActor]!!.ref

    private fun defaultReceive(state: State):Receive = { msg ->
        when {

            msg is ConfigDataRequired -> {
                val map = configDataProvider.configData()

                if(map.isNotEmpty()){
                    val cdr = CfgDataReq.of(map, CfgDataReq.Mod.merge)
                    connectionManager.send(In.ConfigDataFeedback(cdr))
                } else {
                    LOG.info("Config data required ignored because of map is empty")
                }
            }

            msg is DeploymentInfo && state.inDeployment(msg.info.id) ->  child("deploymentManager")!!.send(msg)

            msg is DeploymentInfo && state.inDeployment -> child("deploymentManager")!!.send(CancelForced)

            msg is DeploymentInfo -> {
                val deploymentManager = actorOf("deploymentManager"){ DeploymentManager.of(it) }
                become(defaultReceive(state.copy(deployment = msg)))
                deploymentManager.send(msg)
                LOG.info("DeploymentInfo msg, decreased ping interval to be reactive on server requests (ping: 30s)")
                connectionManager.send(In.SetPing(Duration.standardSeconds(30)))
            }

            msg is DownloadFailed -> {
                become(defaultReceive(state.copy(deployment = null)))
                child("deploymentManager")!!.close()
            }

            msg is UpdateFailed -> {
                LOG.info("UpdateFailed.")
                become(defaultReceive(state.copy(deployment = null)))
                child("deploymentManager")!!.close()
                LOG.info("Restore server ping interval")
                connectionManager.send(In.SetPing(null))
            }

            msg is UpdateFinished -> {
                LOG.info("UpdateFinished.")
                become(defaultReceive(state.copy(deployment = null)))
                child("deploymentManager")!!.close()
                LOG.info("Restore server ping interval")
                connectionManager.send(In.SetPing(null))
            }

            msg is DeploymentCancelInfo && !state.inDeployment && registry.currentUpdateIsCancellable()-> {
                connectionManager.send(In.CancelFeedback(
                        CnclFdbkReq.newInstance(msg.info.cancelAction.stopId,
                                CnclFdbkReq.Sts.Exc.closed,
                                CnclFdbkReq.Sts.Rslt.Fnsh.success)))
                notificationManager.send(MessageListener.Message.State.CancellingUpdate)
                connectionManager.send(In.SetPing(null))
            }

            msg is DeploymentCancelInfo && !registry.currentUpdateIsCancellable() -> {
                connectionManager.send(ConnectionManager.Companion.Message.In.CancelFeedback(
                        CnclFdbkReq.newInstance(msg.info.cancelAction.stopId,
                                CnclFdbkReq.Sts.Exc.rejected,
                                CnclFdbkReq.Sts.Rslt.Fnsh.success,
                                "Update already started. Can't be stopped.")))
            }

            msg is DeploymentCancelInfo -> {
                LOG.warn("DeploymentCancelInfo")
                child("deploymentManager")!!.send(msg)
                LOG.info("Restore server ping interval")
                connectionManager.send(In.SetPing(null))
            }

            state.inDeployment && msg is NoAction ->{
                LOG.warn("CancelForced/RemoveTarget.")
                child("deploymentManager")!!.send(CancelForced)
            }

            msg is UpdateStopped -> {
                LOG.info("update stopped")
                become(defaultReceive(state.copy(deployment = null)))
                LOG.info("Restore server ping interval")
                connectionManager.send(In.SetPing(null))
            }

            msg is NoAction ->{
                notificationManager.send(MessageListener.Message.State.Idle)
            }

            msg is ErrMsg -> {
                LOG.warn("ErrMsg. Not yet implemented")
            }

            else -> unhandled(msg)
        }
    }

    init {
        become(defaultReceive(State()))
        runBlocking { connectionManager.send(In.Register(channel)) }
    }

    companion object {
        fun of(scope: ActorScope) = ActionManager(scope)

        data class State(val deployment: DeploymentInfo? = null) {
            val inDeployment = deployment != null
            fun inDeployment(id: String) = inDeployment && deployment!!.info.id == id
        }

        sealed class Message {

            object CancelForced: Message()
            object UpdateStopped: Message()

        }
    }

}