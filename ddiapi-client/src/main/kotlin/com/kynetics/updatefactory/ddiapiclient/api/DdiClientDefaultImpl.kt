package com.kynetics.updatefactory.ddiapiclient.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.kynetics.updatefactory.ddiapiclient.api.model.*
import com.kynetics.updatefactory.ddiapiclient.security.Authentication
import com.kynetics.updatefactory.ddiapiclient.security.HawkbitAuthenticationRequestInterceptor
import com.kynetics.updatefactory.ddiapiclient.security.UpdateFactoryAuthenticationRequestInterceptor
import com.kynetics.updatefactory.ddiclient.core.api.TargetTokenFoundListener
import com.kynetics.updatefactory.ddiclient.core.api.UpdateFactoryClientData
import com.kynetics.updatefactory.ddiclient.core.api.UpdateFactoryClientData.ServerType.HAWKBIT
import okhttp3.OkHttpClient
import org.slf4j.LoggerFactory
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStream
import java.net.HttpURLConnection
import java.util.*
import java.util.concurrent.Executors


class DdiClientDefaultImpl private constructor(private val ddiRestApi: DdiRestApi, private val tenant:String, private val controllerId:String) : DdiClient {

    override suspend fun getSoftwareModulesArtifacts(softwareModuleId: String): List<ArtfctResp>{
        LOG.debug("getSoftwareModulesArtifacts(%s)", softwareModuleId)
        val artifact = ddiRestApi.getSoftwareModulesArtifacts(tenant, controllerId, softwareModuleId).await()
        if(LOG.isDebugEnabled){
            LOG.debug("$artifact")
        }
        return artifact
    }


    override suspend fun putConfigData(data: CfgDataReq){
        LOG.debug("putConfigData(%s)",CfgDataReq)
        ddiRestApi.putConfigData(tenant, controllerId, data).await()
    }


    override suspend fun getControllerActions(): CtrlBaseResp{
        LOG.debug("getControllerActions()")
        val response = ddiRestApi.getControllerActions(tenant, controllerId).await()
        LOG.debug("%s",response)
        return handleResponse(response)
    }

    override suspend fun onControllerActionsChange(etag: String, onChange: OnResourceChange<CtrlBaseResp>) {
        LOG.debug("onDeploymentActionDetailsChange(%s)", etag)
        val response = ddiRestApi.getControllerActions(tenant, controllerId, etag).await()
        LOG.debug("%s",response)

        handleOnChangeResponse(response, etag, "BaseResource", onChange)
    }

    override suspend fun getDeploymentActionDetails(actionId: String, historyCount: Int): DeplBaseResp {
        LOG.debug("getDeploymentActionDetails($actionId, $historyCount)")
        val response = ddiRestApi.getDeploymentActionDetails(tenant, controllerId, actionId, null, historyCount).await()
        LOG.debug("%s",response)
        return handleResponse(response)
    }

    override suspend fun onDeploymentActionDetailsChange(actionId: String, historyCount: Int, etag: String, onChange: OnResourceChange<DeplBaseResp>) {
        LOG.debug("onDeploymentActionDetailsChange($actionId, $historyCount, $etag)")
        val response = ddiRestApi.getDeploymentActionDetails(tenant, controllerId, actionId, null, historyCount, etag).await()
        LOG.debug("%s",response)
        handleOnChangeResponse(response, etag, "Deployment", onChange)
    }

    override suspend fun getCancelActionDetails(actionId: String): CnclActResp {
        LOG.debug("getCancelActionDetails($actionId)")
        val response = ddiRestApi.getCancelActionDetails(tenant, controllerId, actionId).await()
        LOG.debug("%s",response)
        return response
    }


    override suspend fun postDeploymentActionFeedback(actionId: String, feedback: DeplFdbkReq) {
        LOG.debug("postDeploymentActionFeedback(%s,%s)",actionId, feedback)
        ddiRestApi.postDeploymentActionFeedback(tenant, controllerId, actionId, feedback).await()
    }


    override suspend fun postCancelActionFeedback(actionId: String, feedback: CnclFdbkReq) {
        LOG.debug("postCancelActionFeedback(%s,%s)",actionId, feedback)
        ddiRestApi.postCancelActionFeedback(tenant, controllerId, actionId, feedback).await()
    }

    override suspend fun downloadArtifact(url: String): InputStream {
        LOG.debug("downloadArtifact(%s)",url)
        return ddiRestApi.downloadArtifact(url).await().byteStream()
    }

    private suspend fun <T> handleOnChangeResponse(response: Response<T>, etag: String, resourceName:String, onChange: OnResourceChange<T>) {
        when (response.code()) {
            in 200..299 -> {
                val newEtag = response.headers()[ETAG_HEADER] ?: ""
                LOG.info("%s is changed. Old ETag: %s, new ETag: %s", resourceName,etag,newEtag)
                onChange.invoke(response.body()!!, newEtag)
            }

            HttpURLConnection.HTTP_NOT_MODIFIED -> LOG.info("%s not changed",resourceName)

            else -> throw HttpException(response)
        }
    }

    private fun <T>handleResponse(response: Response<T>): T {
        return when (response.code()) {
            in 200..299 -> response.body()!!
            else -> throw HttpException(response)
        }
    }

    companion object {
        const val ETAG_HEADER = "ETag"

        val LOG = LoggerFactory.getLogger(DdiClient::class.java)!!

        fun of(updateFactoryClientData: UpdateFactoryClientData): DdiClientDefaultImpl {
            val httpBuilder = OkHttpClient.Builder()
            val authentications = HashSet<Authentication>()
            with(updateFactoryClientData){
                if(gatewayToken != null) {
                    authentications.add(Authentication.newInstance(Authentication.AuthenticationType.GATEWAY_TOKEN_AUTHENTICATION, gatewayToken!!))
                }
                if(targetToken != null) {
                    authentications.add(Authentication.newInstance(Authentication.AuthenticationType.TARGET_TOKEN_AUTHENTICATION, targetToken!!))
                }
                httpBuilder.interceptors().add(0, if (serverType == HAWKBIT)
                    HawkbitAuthenticationRequestInterceptor(authentications)
                else
                    UpdateFactoryAuthenticationRequestInterceptor(authentications, targetTokenFoundListener ?: object:TargetTokenFoundListener{}))
                val ddiRestApi = Retrofit.Builder()
                        .baseUrl(serverUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(CoroutineCallAdapterFactory())
                        .callbackExecutor(Executors.newSingleThreadExecutor())
                        .client(httpBuilder.build())
                        .build()
                        .create(DdiRestApi::class.java)
                return DdiClientDefaultImpl(ddiRestApi, tenant, controllerId)

            }
        }
    }
}