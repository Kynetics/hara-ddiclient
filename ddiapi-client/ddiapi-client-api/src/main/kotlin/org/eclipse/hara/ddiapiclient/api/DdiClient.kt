/*
* Copyright © 2017-2021  Kynetics  LLC
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*/

package org.eclipse.hara.ddiapiclient.api

import org.eclipse.hara.ddiapiclient.api.model.ArtfctResp
import org.eclipse.hara.ddiapiclient.api.model.CfgDataReq
import org.eclipse.hara.ddiapiclient.api.model.CnclActResp
import org.eclipse.hara.ddiapiclient.api.model.CnclFdbkReq
import org.eclipse.hara.ddiapiclient.api.model.CtrlBaseResp
import org.eclipse.hara.ddiapiclient.api.model.DeplBaseResp
import org.eclipse.hara.ddiapiclient.api.model.DeplFdbkReq
import java.io.InputStream

typealias OnResourceChange<T> = suspend (T, String) -> Unit

interface DdiClient {

    suspend fun getControllerActions(): CtrlBaseResp

    suspend fun onControllerActionsChange(etag: String = "", onChange: OnResourceChange<CtrlBaseResp>)

    suspend fun getDeploymentActionDetails(actionId: String, historyCount: Int = -1): DeplBaseResp

    suspend fun onDeploymentActionDetailsChange(actionId: String, historyCount: Int = -1, etag: String = "", onChange: OnResourceChange<DeplBaseResp>)

    suspend fun getCancelActionDetails(actionId: String): CnclActResp

    suspend fun getSoftwareModulesArtifacts(softwareModuleId: String): List<ArtfctResp>

    suspend fun postDeploymentActionFeedback(actionId: String, feedback: DeplFdbkReq)

    suspend fun postCancelActionFeedback(actionId: String, feedback: CnclFdbkReq)

    suspend fun putConfigData(data: CfgDataReq, onSuccessConfigData: () -> Unit)

    suspend fun downloadArtifact(url: String): InputStream
}
