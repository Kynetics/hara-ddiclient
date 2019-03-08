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
import java.util.HashMap

data class DdiCancel(val id: String, val cancelAction: DdiCancelActionToStop)

data class DdiControllerBase(val config: DdiConfig, @SerializedName("_links") val links : Map<String, LinkEntry>) : ResourceSupport(links) {
    val requireDeploynet = getLink("deploymentBase") != null
    val requireCancel = getLink("cancelAction") != null
    fun requireConfigData() = links["configData"] != null
    val actionId = getLink("deploymentBase")?.actionId

}

data class DdiDeploymentBase(@SerializedName("id") private val deplyomentId: String,
                             @SerializedName("deployment") val deployment: DdiDeployment,
                             @SerializedName("actionHistory")val actionHistory: DdiActionHistory? = null) : ResourceSupport()

data class Error (@SerializedName(value = "errorCode", alternate = ["status"]) val errorCode: String? = null,
                  @SerializedName(value = "exceptionClass", alternate = ["exception"]) val exceptionClass: String? = null,
                  var code: Int = 0,
                  val message: String? = null,
                  val parameters: List<String>? = null)