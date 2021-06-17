/*
 * Copyright © 2017-2021  Kynetics  LLC
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.hara.updatefactory.ddiclient.integrationtest

import org.eclipse.hara.updatefactory.ddiclient.core.UpdateFactoryClientDefaultImpl
import org.eclipse.hara.updatefactory.ddiclient.core.api.ConfigDataProvider
import org.eclipse.hara.updatefactory.ddiclient.core.api.DeploymentPermitProvider
import org.eclipse.hara.updatefactory.ddiclient.core.api.DirectoryForArtifactsProvider
import org.eclipse.hara.updatefactory.ddiclient.core.api.MessageListener
import org.eclipse.hara.updatefactory.ddiclient.core.api.UpdateFactoryClient
import org.eclipse.hara.updatefactory.ddiclient.core.api.UpdateFactoryClientData
import org.eclipse.hara.updatefactory.ddiclient.core.api.Updater
import org.eclipse.hara.updatefactory.ddiclient.integrationtest.TestUtils.basic
import org.eclipse.hara.updatefactory.ddiclient.integrationtest.TestUtils.gatewayToken
import org.eclipse.hara.updatefactory.ddiclient.integrationtest.TestUtils.getDownloadDirectoryFromActionId
import org.eclipse.hara.updatefactory.ddiclient.integrationtest.TestUtils.tenantName
import org.eclipse.hara.updatefactory.ddiclient.integrationtest.TestUtils.ufUrl
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.joda.time.Duration
import org.testng.Assert
import java.io.File
import java.util.LinkedList

abstract class AbstractClientTest {

    protected var client: UpdateFactoryClient? = null

    private val queue = LinkedList<() -> Unit >()

    protected open fun defaultClientFromTargetId(
            directoryDataProvider: DirectoryForArtifactsProvider = TestUtils.directoryDataProvider,
            configDataProvider: ConfigDataProvider = TestUtils.configDataProvider,
            updater: Updater = TestUtils.updater,
            messageListeners: List<MessageListener> = emptyList(),
            deploymentPermitProvider: DeploymentPermitProvider = object : DeploymentPermitProvider {}
    ): (String) -> UpdateFactoryClient = { targetId ->
        val clientData = UpdateFactoryClientData(
                tenantName,
                targetId,
                ufUrl,
                UpdateFactoryClientData.ServerType.UPDATE_FACTORY,
                gatewayToken)

        val client = UpdateFactoryClientDefaultImpl()

        val eventListener = object : MessageListener {
            override fun onMessage(message: MessageListener.Message) {
                when (message) {

                    is MessageListener.Message.Event.UpdateFinished, MessageListener.Message.State.CancellingUpdate -> {
                        queue.poll().invoke()
                    }

                    else -> { println(message) }
                }
            }
        }

        client.init(
                clientData,
                directoryDataProvider,
                configDataProvider,
                deploymentPermitProvider,
                listOf(eventListener, *messageListeners.toTypedArray()),
                updater
        )
        client
    }

    // todo refactor test
    protected fun testTemplate(
            deployment: TestUtils.TargetDeployments,
            timeout: Long = Duration.standardSeconds(15).millis,
            clientFromTargetId: (String) -> UpdateFactoryClient = defaultClientFromTargetId()
    ) = runBlocking {

        withTimeout(timeout) {
            client = clientFromTargetId(deployment.targetId)
            val managementApi = ManagementClient.newInstance(ufUrl)

            deployment.deploymentInfo.forEach { deploymentInfo ->

                var actionStatus = managementApi.getTargetActionStatusAsync(basic, deployment.targetId, deploymentInfo.actionId)

                Assert.assertEquals(actionStatus, deploymentInfo.actionStatusOnStart)

                queue.add {
                    launch {
                        while(managementApi.getActionAsync(basic, deployment.targetId, deploymentInfo.actionId)
                                .status != Action.Status.finished
                        ) {
                            delay(100)
                        }
                        actionStatus = managementApi.getTargetActionStatusAsync(basic, deployment.targetId, deploymentInfo.actionId)

                        Assert.assertEquals(actionStatus.content, deploymentInfo.actionStatusOnFinish.content)

                        deploymentInfo.filesDownloadedPairedWithServerFile.forEach { (fileDownloaded, serverFile) ->
                            println(File(fileDownloaded).absolutePath)
                            println(File(serverFile).absolutePath)
                            Assert.assertEquals(File(fileDownloaded).readText(), File(serverFile).readText())
                        }

                        getDownloadDirectoryFromActionId(deploymentInfo.actionId.toString()).deleteRecursively()
                    }
                }
            }
            client?.startAsync()
            launch {
                while (queue.isNotEmpty()) {
                    delay(500) }
            }
        }
    }
}
