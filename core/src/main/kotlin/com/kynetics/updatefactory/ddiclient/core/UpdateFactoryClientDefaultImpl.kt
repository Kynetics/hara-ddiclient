package com.kynetics.updatefactory.ddiclient.core

import com.kynetics.updatefactory.ddiapiclient.api.DdiClientDefaultImpl
import com.kynetics.updatefactory.ddiclient.core.actors.AbstractActor
import com.kynetics.updatefactory.ddiclient.core.actors.ActorRef
import com.kynetics.updatefactory.ddiclient.core.actors.ConnectionManager
import com.kynetics.updatefactory.ddiclient.core.actors.RootActor
import com.kynetics.updatefactory.ddiclient.core.actors.UFClientContext
import com.kynetics.updatefactory.ddiclient.core.api.ConfigDataProvider
import com.kynetics.updatefactory.ddiclient.core.api.DeploymentPermitProvider
import com.kynetics.updatefactory.ddiclient.core.api.DirectoryForArtifactsProvider
import com.kynetics.updatefactory.ddiclient.core.api.MessageListener
import com.kynetics.updatefactory.ddiclient.core.api.UpdateFactoryClient
import com.kynetics.updatefactory.ddiclient.core.api.UpdateFactoryClientData
import com.kynetics.updatefactory.ddiclient.core.api.Updater
import kotlinx.coroutines.runBlocking

class UpdateFactoryClientDefaultImpl : UpdateFactoryClient {

    var rootActor: ActorRef? = null

    override fun init(
        updateFactoryClientData: UpdateFactoryClientData,
        directoryForArtifactsProvider: DirectoryForArtifactsProvider,
        configDataProvider: ConfigDataProvider,
        deploymentPermitProvider: DeploymentPermitProvider,
        messageListeners: List<MessageListener>,
        vararg updaters: Updater
    ) {
        rootActor = AbstractActor.actorOf("rootActor", UFClientContext(
                DdiClientDefaultImpl.of(updateFactoryClientData),
                UpdaterRegistry(*updaters),
                configDataProvider,
                PathResolver(directoryForArtifactsProvider),
                deploymentPermitProvider,
                messageListeners
        )) { RootActor.of(it) }
    }

    override fun startAsync() = runBlocking { rootActor!!.send(ConnectionManager.Companion.Message.In.Start) }

    override fun stop() = runBlocking { rootActor!!.send(ConnectionManager.Companion.Message.In.Stop) }

    override fun forcePing() = runBlocking { rootActor!!.send(ConnectionManager.Companion.Message.In.ForcePing) }
}
