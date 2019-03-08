/*
 * Copyright © 2017-2019 Kynetics LLC
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 */

package com.kynetics.updatefactory.ddiapiclient.api.model

import com.google.gson.annotations.SerializedName

data class DdiCancel(val id: String, val cancelAction: DdiCancelActionToStop)

data class DdiControllerBase(val config: DdiConfig,
                             @SerializedName("_links") val links : Resource = emptyMap()) {
    fun requireDeployment() = links[LinkType.DEPLOYMENT] != null
    fun requireCancel() = links[LinkType.CANCEL] != null
    fun requireConfigData() = links[LinkType.CONFIG_DATA] != null
    fun actionId() = LinkType.DEPLOYMENT.getActionId(links[LinkType.DEPLOYMENT])!!

}

data class DdiDeploymentBase(@SerializedName("id") val deplyomentId: String,
                             @SerializedName("deployment") val deployment: DdiDeployment,
                             @SerializedName("actionHistory")val actionHistory: DdiActionHistory? = null)

data class Error (@SerializedName(value = "errorCode", alternate = ["status"]) val errorCode: String? = null,
                  @SerializedName(value = "exceptionClass", alternate = ["exception"]) val exceptionClass: String? = null,
                  var code: Int = 0,
                  val message: String? = null,
                  val parameters: List<String>? = null)