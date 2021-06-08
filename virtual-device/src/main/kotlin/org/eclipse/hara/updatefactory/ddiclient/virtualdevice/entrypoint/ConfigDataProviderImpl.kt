/*
 * Copyright © 2017-2021  Kynetics  LLC
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.hara.updatefactory.ddiclient.virtualdevice.entrypoint

import org.eclipse.hara.updatefactory.ddiclient.core.api.ConfigDataProvider
import org.eclipse.hara.updatefactory.ddiclient.core.api.UpdateFactoryClientData
import org.eclipse.hara.updatefactory.ddiclient.virtualdevice.Configuration
import java.text.MessageFormat

class ConfigDataProviderImpl(
    private val virtualDeviceId:Int,
    private val clientData: UpdateFactoryClientData): ConfigDataProvider {
    override fun configData(): Map<String, String> {
        return Configuration.targetAttributes
            .split("|")
            .map { it.split(",").let { list -> list[0] to MessageFormat.format(list[1],
                virtualDeviceId,
                clientData.tenant,
                clientData.controllerId,
                clientData.gatewayToken) }
            }
            .toMap()
    }
}