package com.kynetics.updatefactory.ddiclient.core.actors

import com.kynetics.updatefactory.ddiapiclient.api.model.DeplFdbkReq
import com.kynetics.updatefactory.ddiclient.core.actors.ConnectionManager.Companion.Message.Out.DeploymentInfo
import com.kynetics.updatefactory.ddiclient.core.api.Updater
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.joda.time.Instant
import java.io.File
import com.kynetics.updatefactory.ddiclient.core.actors.ConnectionManager.Companion.Message.In.DeploymentFeedback

@UseExperimental(ObsoleteCoroutinesApi::class)
class UpdateManager
private constructor(scope: ActorScope): AbstractActor(scope) {

    private val registry = coroutineContext[UFClientContext]!!.registry
    private val dfap      = coroutineContext[UFClientContext]!!.directoryForArtifactsProvider
    private val connectionManager = coroutineContext[CMActor]!!.ref

    private fun beforeStartReceive(): Receive = { msg ->
        when(msg) {

            is DeploymentInfo -> {
                LOG.info("START UPDATING!!!")
                val updaters = registry.allUpdatersWithSwModulesOrderedForPriority(msg.info.deployment.chunks)
                //TODO make it recursive
                var failed=false
                updaters.forEachIndexed loop@{ indx, it ->
                    val success = it.updater.apply(it.softwareModules.map { swModule ->
                        convert(swModule, pathCalculator(msg.info.id)) }.toSet(), object: Updater.Messanger{
                        override fun sendMessageToServer(msgStr: String) {
                            runBlocking {
                                connectionManager.send(DeploymentFeedback(DeplFdbkReq(msg.info.id , Instant.now().toString(),DeplFdbkReq.Sts(
                                    DeplFdbkReq.Sts.Exc.proceeding,
                                    DeplFdbkReq.Sts.Rslt(
                                            DeplFdbkReq.Sts.Rslt.Fnsh.none,
                                            DeplFdbkReq.Sts.Rslt.Prgrs(updaters.size, indx)),
                                    listOf(msgStr)))))
                            }
                        }
                    })
                    if(!success) {
                        LOG.warn("update ${indx} failed!")
                        parent!!.send(DeploymentManager.Companion.Message.UpdateFailed)
                        connectionManager.send(DeploymentFeedback((DeplFdbkReq(msg.info.id , Instant.now().toString(),DeplFdbkReq.Sts(
                                DeplFdbkReq.Sts.Exc.closed,
                                DeplFdbkReq.Sts.Rslt(
                                        DeplFdbkReq.Sts.Rslt.Fnsh.failure,
                                        DeplFdbkReq.Sts.Rslt.Prgrs(updaters.size, indx)),
                                listOf("Update failed"))))))
                        failed = true
                        return@loop
                    }
                }
                if(!failed){
                    parent!!.send(DeploymentManager.Companion.Message.UpdateFinished)
                    connectionManager.send(DeploymentFeedback(DeplFdbkReq(msg.info.id , Instant.now().toString(),DeplFdbkReq.Sts(
                            DeplFdbkReq.Sts.Exc.closed,
                            DeplFdbkReq.Sts.Rslt(
                                    DeplFdbkReq.Sts.Rslt.Fnsh.success,
                                    DeplFdbkReq.Sts.Rslt.Prgrs(updaters.size, updaters.size)),
                            listOf("Update finished")))))
                }
            }

            else -> unhandled(msg)
        }

    }

    private fun pathCalculator(id: String):(artifact: Updater.SwModule.Artifact) -> String {
        return { artifact ->
            File(dfap.directoryForArtifacts(id), artifact.hashes.md5).absolutePath
        }
    }

    private fun convert (swModule: Updater.SwModule, pathCalculator: (Updater.SwModule.Artifact) -> String): Updater.SwModuleWithPath =
            Updater.SwModuleWithPath(
                    swModule.metadata?.map { Updater.SwModuleWithPath.Metadata(it.key, it.value) }?.toSet(),
                    swModule.type,
                    swModule.name,
                    swModule.version,
                    swModule.artifacts.map
                    { Updater.SwModuleWithPath.Artifact(it.filename,it.hashes,it.size, pathCalculator(it))}.toSet()
            )

    init {
        become(beforeStartReceive())
    }

    companion object {
        fun of(scope: ActorScope) = UpdateManager(scope)
    }
}

