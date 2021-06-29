/*
 * Copyright © 2017-2021  Kynetics  LLC
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.hara.ddiclient.virtualdevice.entrypoint

import org.eclipse.hara.ddiclient.core.api.DirectoryForArtifactsProvider
import org.eclipse.hara.ddiclient.virtualdevice.Configuration
import java.io.File

class DirectoryForArtifactsProviderImpl(private val controllerId:String): DirectoryForArtifactsProvider {
    override fun directoryForArtifacts(): File = File("${Configuration.storagePath}/$controllerId")
}