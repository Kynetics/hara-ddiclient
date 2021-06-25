/*
 * Copyright © 2017-2021  Kynetics  LLC
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.hara.updatefactory.ddiclient.core.api

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred

interface DeploymentPermitProvider {

    fun downloadAllowed(): Deferred<Boolean> = CompletableDeferred(true)

    fun updateAllowed(): Deferred<Boolean> = CompletableDeferred(true)
}
