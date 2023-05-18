package com.mikyegresl.valostat.base.network.model.agent

import com.google.gson.annotations.SerializedName

data class AgentVoiceLineResponse(
    @SerializedName("minDuration")
    val minDuration: Double? = null,
    @SerializedName("maxDuration")
    val maxDuration: Double? = null,
    @SerializedName("mediaList")
    val mediaList: List<VoiceLineMediaResponse>? = null
) {
    data class VoiceLineMediaResponse(
        @SerializedName("id")
        val id: Int? = null,
        @SerializedName("wave")
        val wave: String? = null,
        @SerializedName("wwise")
        val wwise: String? = null
    )
}
