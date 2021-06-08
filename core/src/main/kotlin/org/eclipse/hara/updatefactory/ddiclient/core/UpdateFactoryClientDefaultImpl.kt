/*
 * Copyright © 2017-2021  Kynetics  LLC
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.hara.updatefactory.ddiclient.core

import org.eclipse.hara.updatefactory.ddiapiclient.api.DdiClientDefaultImpl
import org.eclipse.hara.updatefactory.ddiclient.core.actors.AbstractActor
import org.eclipse.hara.updatefactory.ddiclient.core.actors.ActorRef
import org.eclipse.hara.updatefactory.ddiclient.core.actors.ConnectionManager
import org.eclipse.hara.updatefactory.ddiclient.core.actors.RootActor
import org.eclipse.hara.updatefactory.ddiclient.core.actors.UFClientContext
import org.eclipse.hara.updatefactory.ddiclient.core.api.ConfigDataProvider
import org.eclipse.hara.updatefactory.ddiclient.core.api.DeploymentPermitProvider
import org.eclipse.hara.updatefactory.ddiclient.core.api.DirectoryForArtifactsProvider
import org.eclipse.hara.updatefactory.ddiclient.core.api.MessageListener
import org.eclipse.hara.updatefactory.ddiclient.core.api.UpdateFactoryClient
import org.eclipse.hara.updatefactory.ddiclient.core.api.UpdateFactoryClientData
import org.eclipse.hara.updatefactory.ddiclient.core.api.Updater
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

    override fun stop() = runBlocking {
        if (!rootActor!!.isClosedForSend) {
            rootActor!!.send(ConnectionManager.Companion.Message.In.Stop)
        }
    }

    override fun forcePing() = runBlocking { rootActor!!.send(ConnectionManager.Companion.Message.In.ForcePing) }
}
