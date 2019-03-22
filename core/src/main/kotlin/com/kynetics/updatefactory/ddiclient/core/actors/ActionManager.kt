package com.kynetics.updatefactory.ddiclient.core.actors

import com.kynetics.updatefactory.ddiapiclient.api.model.CfgDataReq
import com.kynetics.updatefactory.ddiapiclient.api.model.CnclFdbkReq
import com.kynetics.updatefactory.ddiapiclient.api.model.DeplFdbkReq
import com.kynetics.updatefactory.ddiclient.core.actors.ConnectionManager.Companion.Message.In
import com.kynetics.updatefactory.ddiclient.core.actors.ConnectionManager.Companion.Message.Out.*
import com.kynetics.updatefactory.ddiclient.core.actors.ConnectionManager.Companion.Message.Out.Err.ErrMsg
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.runBlocking
import com.kynetics.updatefactory.ddiclient.core.actors.DeploymentManager.Companion.Message.DownloadFailed
import com.kynetics.updatefactory.ddiclient.core.actors.DeploymentManager.Companion.Message.*
import kotlinx.coroutines.delay
import org.joda.time.Duration

//TODO set frequent ping during deployment
@UseExperimental(ObsoleteCoroutinesApi::class)
class ActionManager
private constructor(scope: ActorScope): AbstractActor(scope) {

    private val configDataProvider = coroutineContext[UFClientContext]!!.configDataProvider
    private val connectionManager  = coroutineContext[CMActor]!!.ref

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

            msg is DeploymentInfo && state.alreadyProcessing(msg) -> LOG.info("Skip unchanged deployment info")

            msg is DeploymentInfo && state.inDeployment(msg.info.id) ->  child("deploymentManager")!!.send(msg)

            msg is DeploymentInfo && state.inDeployment -> LOG.info("HANGED DEPLOYMENT ID ???")

            msg is DeploymentInfo -> {
                val deploymentManager = actorOf("deploymentManager"){ DeploymentManager.of(it) }
                become(defaultReceive(state.copy(deployment = msg)))
                deploymentManager.send(msg)
                LOG.info("DeploymentInfo msg, decreased ping interval to be reactive on server requests (ping: 30s)")
                connectionManager.send(In.SetPing(Duration.standardSeconds(30)))
            }

            msg is DownloadFailed -> {
                LOG.warn("DownloadFailed. Not yet implemented")
                become(defaultReceive(state.copy(deployment = null)))
                child("deploymentManager")!!.close()

            }

            msg is UpdateFailed -> {
                LOG.info("UpdateFailed.")
                become(defaultReceive(state.copy(deployment = null)))
                child("deploymentManager")!!.close()
                connectionManager.send(In.SetPing(null))
                LOG.info("Restore server ping interval")
            }

            msg is UpdateFinished -> {
                LOG.info("UpdateFinished.")
                become(defaultReceive(state.copy(deployment = null)))
                child("deploymentManager")!!.close()
                connectionManager.send(In.SetPing(null))
                LOG.info("Restore server ping interval")
            }

            msg is DeploymentCancelInfo && !state.inDeployment -> {
                LOG.info("DeploymentCancelInfo, decreased ping interval to check fast if new action exist (ping: 1s)")
                connectionManager.send(In.CancelFeedback(
                        CnclFdbkReq.newInstance(msg.info.cancelAction.stopId,
                                CnclFdbkReq.Sts.Exc.closed,
                                CnclFdbkReq.Sts.Rslt.Fnsh.success)))
                connectionManager.send(In.SetPing(Duration.standardSeconds(1)))
            }

            msg is DeploymentCancelInfo -> {
                LOG.warn("DeploymentCancelInfo")
            }

            state.inDeployment && msg is NoAction ->{
                LOG.warn("ForceCancel/RemoveTarget. Not yet implemented")
            }

            msg is NoAction ->{ }

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
            fun alreadyProcessing(msg: DeploymentInfo) = inDeployment && deployment!!.equalsApartHistory(msg)
        }
    }

}