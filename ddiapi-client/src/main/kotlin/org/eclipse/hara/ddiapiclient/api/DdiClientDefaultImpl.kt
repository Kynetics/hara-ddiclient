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
import org.eclipse.hara.ddiapiclient.security.Authentication
import org.eclipse.hara.ddiapiclient.security.HawkbitAuthenticationRequestInterceptor
import org.eclipse.hara.ddiclient.core.api.HaraClientData.ServerType.HAWKBIT
import java.io.InputStream
import java.net.HttpURLConnection
import java.util.HashSet
import java.util.concurrent.Executors
import okhttp3.OkHttpClient
import org.eclipse.hara.ddiclient.core.api.HaraClientData
import org.slf4j.LoggerFactory
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DdiClientDefaultImpl private constructor(private val ddiRestApi: DdiRestApi, private val tenant: String, private val controllerId: String) : DdiClient {

    override suspend fun getSoftwareModulesArtifacts(softwareModuleId: String): List<ArtfctResp> {
        LOG.debug("getSoftwareModulesArtifacts({})", softwareModuleId)
        val artifact = ddiRestApi.getSoftwareModulesArtifacts(tenant, controllerId, softwareModuleId)
        LOG.debug("{}", artifact)
        return artifact
    }

    override suspend fun putConfigData(data: CfgDataReq, onSuccessConfigData: () -> Unit) {
        LOG.debug("putConfigData({})", CfgDataReq)
        val responseCode = ddiRestApi.putConfigData(tenant, controllerId, data).code()
        if (responseCode in 200 until 300) {
            onSuccessConfigData.invoke()
        }
    }

    override suspend fun getControllerActions(): CtrlBaseResp {
        LOG.debug("getControllerActions()")
        val response = ddiRestApi.getControllerActions(tenant, controllerId)
        LOG.debug("{}", response)
        return handleResponse(response)
    }

    override suspend fun onControllerActionsChange(etag: String, onChange: OnResourceChange<CtrlBaseResp>) {
        LOG.debug("onDeploymentActionDetailsChange({})", etag)
        val response = ddiRestApi.getControllerActions(tenant, controllerId, etag)
        LOG.debug("{}", response)
        handleOnChangeResponse(response, etag, "BaseResource", onChange)
    }

    override suspend fun getDeploymentActionDetails(actionId: String, historyCount: Int): DeplBaseResp {
        LOG.debug("getDeploymentActionDetails($actionId, $historyCount)")
        val response = ddiRestApi.getDeploymentActionDetails(tenant, controllerId, actionId, null, historyCount)
        LOG.debug("{}", response)
        return handleResponse(response)
    }

    override suspend fun onDeploymentActionDetailsChange(actionId: String, historyCount: Int, etag: String, onChange: OnResourceChange<DeplBaseResp>) {
        LOG.debug("onDeploymentActionDetailsChange($actionId, $historyCount, $etag)")
        val response = ddiRestApi.getDeploymentActionDetails(tenant, controllerId, actionId, null, historyCount, etag)
        LOG.debug("{}", response)
        handleOnChangeResponse(response, etag, "Deployment", onChange)
    }

    override suspend fun getCancelActionDetails(actionId: String): CnclActResp {
        LOG.debug("getCancelActionDetails($actionId)")
        val response = ddiRestApi.getCancelActionDetails(tenant, controllerId, actionId)
        LOG.debug("{}", response)
        return response
    }

    override suspend fun postDeploymentActionFeedback(actionId: String, feedback: DeplFdbkReq) {
        LOG.debug("postDeploymentActionFeedback({},{})", actionId, feedback)
        ddiRestApi.postDeploymentActionFeedback(tenant, controllerId, actionId, feedback)
    }

    override suspend fun postCancelActionFeedback(actionId: String, feedback: CnclFdbkReq) {
        LOG.debug("postCancelActionFeedback({},{})", actionId, feedback)
        ddiRestApi.postCancelActionFeedback(tenant, controllerId, actionId, feedback)
    }

    override suspend fun downloadArtifact(url: String): InputStream {
        LOG.debug("downloadArtifact({})", url)
        return ddiRestApi.downloadArtifact(url).byteStream()
    }

    private suspend fun <T> handleOnChangeResponse(response: Response<T>, etag: String, resourceName: String, onChange: OnResourceChange<T>) {
        when (response.code()) {
            in 200..299 -> {
                val newEtag = response.headers()[ETAG_HEADER] ?: ""
                LOG.info("{} is changed. Old ETag: {}, new ETag: {}", resourceName, etag, newEtag)
                onChange.invoke(response.body()!!, newEtag)
            }

            HttpURLConnection.HTTP_NOT_MODIFIED -> LOG.info("{} not changed", resourceName)

            else -> throw HttpException(response)
        }
    }

    private fun <T> handleResponse(response: Response<T>): T {
        return when (response.code()) {
            in 200..299 -> response.body()!!
            else -> throw HttpException(response)
        }
    }

    companion object {
        const val ETAG_HEADER = "ETag"

        val LOG = LoggerFactory.getLogger(DdiClient::class.java)!!

        fun of(haraClientData: HaraClientData): DdiClientDefaultImpl {
            val httpBuilder = OkHttpClient.Builder()
            val authentications = HashSet<Authentication>()
            with(haraClientData) {
                if (gatewayToken != null) {
                    authentications.add(Authentication.newInstance(Authentication.AuthenticationType.GATEWAY_TOKEN_AUTHENTICATION, gatewayToken!!))
                }
                if (targetToken != null) {
                    authentications.add(Authentication.newInstance(Authentication.AuthenticationType.TARGET_TOKEN_AUTHENTICATION, targetToken!!))
                }
                httpBuilder.interceptors().add(0, HawkbitAuthenticationRequestInterceptor(authentications))
                val ddiRestApi = Retrofit.Builder()
                        .baseUrl(serverUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .callbackExecutor(Executors.newSingleThreadExecutor())
                        .client(httpBuilder.build())
                        .build()
                        .create(DdiRestApi::class.java)
                return DdiClientDefaultImpl(ddiRestApi, tenant, controllerId)
            }
        }
    }
}
