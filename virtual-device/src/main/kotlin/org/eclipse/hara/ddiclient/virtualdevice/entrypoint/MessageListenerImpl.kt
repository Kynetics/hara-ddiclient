/*
 * Copyright © 2017-2021  Kynetics  LLC
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.hara.ddiclient.virtualdevice.entrypoint

import org.eclipse.hara.ddiclient.core.api.HaraClientData
import org.eclipse.hara.ddiclient.core.api.MessageListener
import org.eclipse.hara.ddiclient.virtualdevice.Configuration
import java.text.MessageFormat

class MessageListenerImpl(
    private val virtualDeviceId:Int,
    private val clientData: HaraClientData
): MessageListener {
    override fun onMessage(message: MessageListener.Message) {
        println(
            MessageFormat.format(
            Configuration.logMessageTemplate,
            virtualDeviceId,
            clientData.tenant,
            clientData.controllerId,
            clientData.gatewayToken,message))
    }
}