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

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

typealias Resource = Map<LinkType, LinkEntry>
enum class LinkType(@Expose(deserialize = false, serialize = false)val linkName: String){

    @SerializedName("deploymentBase")
    DEPLOYMENT("deploymentBase"),
    @SerializedName("cancelAction")
    CANCEL("cancelAction"),
    @SerializedName("configData")
    CONFIG_DATA("configData");

    fun getActionId(linkEntry: LinkEntry?):Long?{
        val regex = """.*$linkName/(\d+)""".toRegex()
        return regex.find(linkEntry?.href ?: "")
                ?.destructured
                ?.component1()
                ?.toLong()
    }

}


data class LinkEntry(val href: String)

data class DdiDeployment(val download: HandlingType, val update: HandlingType, val chunks: List<DdiChunk>){

    /**
     * The handling type for the update action.
     */
    enum class HandlingType constructor(val handlingTypeName: String) {

        /**
         * Not necessary for the command.
         */
        @SerializedName("skip")
        SKIP("skip"),

        /**
         * Try to execute (local applications may intervene by SP control API).
         */
        @SerializedName("attempt")
        ATTEMPT("attempt"),

        /**
         * Execution independent of local intervention attempts.
         */
        @SerializedName("forced")
        FORCED("forced")
    }


}
data class DdiChunk(val part: String, val version: String, val name: String, val artifacts: List<DdiArtifact>)
data class DdiArtifact(val fileName:String, val hashes: DdiArtifactHash, val size: Long, @SerializedName("_links") val links : Resource = emptyMap())
data class DdiArtifactHash(val sha1:String? = null, val md5:String? = null)

data class DdiCancelActionToStop(val stopId: String)

data class DdiConfig(val polling: DdiPolling)
data class DdiPolling(val sleep: String)

data class DdiActionHistory(
        @SerializedName("status")val actionStatus: String? = null,
        @SerializedName("messages") val messages: List<String>? = null)


/**
 * Details status information concerning the action processing.
 *
 */
data class DdiStatus(val execution: ExecutionStatus, val result: DdiResult, private val details: List<String> = Collections.emptyList())
/**
 * The element status contains information about the execution of the
 * operation.
 *
 */
enum class ExecutionStatus constructor(@SerializedName("name")val statusName: String) {
    /**
     * Execution of the action has finished.
     */
    @SerializedName("closed")
    CLOSED("closed"),

    /**
     * Execution has started but has not yet finished.
     */
    @SerializedName("proceeding")
    PROCEEDING("proceeding"),

    /**
     * Execution was suspended from outside.
     */
    @SerializedName("canceled")
    CANCELED("canceled"),

    /**
     * Action has been noticed and is intended to run.
     */
    @SerializedName("scheduled")
    SCHEDULED("scheduled"),

    /**
     * Action was not accepted.
     */
    @SerializedName("rejected")
    REJECTED("rejected"),

    /**
     * Action is started after a reset, power loss, etc.
     */
    @SerializedName("resumed")
    RESUMED("resumed")
}
/**
 * Result information of the action progress which can by an intermediate or
 * final update.
 *
 */
data class DdiResult(val finished: FinalResult, val progress: DdiProgress)
/**
 * Defined status of the final result.
 *
 */
enum class FinalResult constructor(val resultName: String) {
    /**
     * Execution was successful.
     */
    @SerializedName("success")
    SUCESS("success"),

    /**
     * Execution terminated with errors or without the expected result.
     */
    @SerializedName("failure")
    FAILURE("failure"),

    /**
     * No final result could be determined (yet).
     */
    @SerializedName("none")
    NONE("none")
}
data class DdiProgress(val cnt: Int, val of: Int)
