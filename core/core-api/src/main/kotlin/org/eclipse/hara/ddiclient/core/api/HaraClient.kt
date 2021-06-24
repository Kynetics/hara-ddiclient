/*
 * Copyright © 2017-2021  Kynetics  LLC
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.hara.ddiclient.core.api

interface HaraClient {

    fun init(
            haraClientData: HaraClientData,
            directoryForArtifactsProvider: DirectoryForArtifactsProvider,
            configDataProvider: ConfigDataProvider,
            deploymentPermitProvider: DeploymentPermitProvider,
            messageListeners: List<MessageListener>,
            vararg updaters: Updater
    )

    fun startAsync()

    fun stop()

    fun forcePing()
}
