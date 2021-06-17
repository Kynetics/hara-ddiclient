/*
 * Copyright © 2017-2021  Kynetics  LLC
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.hara.updatefactory.ddiclient.virtualdevice

import kotlinx.coroutines.*
import org.eclipse.hara.updatefactory.ddiclient.core.UpdateFactoryClientDefaultImpl
import org.eclipse.hara.updatefactory.ddiclient.core.api.UpdateFactoryClientData
import org.eclipse.hara.updatefactory.ddiclient.virtualdevice.entrypoint.ConfigDataProviderImpl
import org.eclipse.hara.updatefactory.ddiclient.virtualdevice.entrypoint.DeploymentPermitProviderImpl
import org.eclipse.hara.updatefactory.ddiclient.virtualdevice.entrypoint.DirectoryForArtifactsProviderImpl
import org.eclipse.hara.updatefactory.ddiclient.virtualdevice.entrypoint.MessageListenerImpl
import org.eclipse.hara.updatefactory.ddiclient.virtualdevice.entrypoint.UpdaterImpl
import kotlin.random.Random.Default.nextInt
import kotlin.random.Random.Default.nextLong

// TODO add exception handling ! --> A
@OptIn(DelicateCoroutinesApi::class)
@ObsoleteCoroutinesApi
fun main() = runBlocking {
    Configuration.apply {
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, logelLevel)

        repeat(poolSize) {
            val clientData = UpdateFactoryClientData(
                tenant,
                controllerIdGenerator.invoke(),
                url,
                UpdateFactoryClientData.ServerType.UPDATE_FACTORY,
                gatewayToken
            )

            GlobalScope.launch {
                val delay = nextLong(0, virtualDeviceStartingDelay)
                println("Virtual Device $it starts in $delay milliseconds")
                delay(delay)
                getClient(clientData, it).startAsync()
            }
        }
    }

    while (true) {}
}

private fun getClient(clientData: UpdateFactoryClientData, virtualDeviceId: Int): UpdateFactoryClientDefaultImpl {
    val client = UpdateFactoryClientDefaultImpl()
    client.init(
        clientData,
        DirectoryForArtifactsProviderImpl(clientData.controllerId),
        ConfigDataProviderImpl(virtualDeviceId, clientData),
        DeploymentPermitProviderImpl(),
        listOf(MessageListenerImpl(virtualDeviceId, clientData)),
        UpdaterImpl(virtualDeviceId, clientData)
    )
    return client
}
