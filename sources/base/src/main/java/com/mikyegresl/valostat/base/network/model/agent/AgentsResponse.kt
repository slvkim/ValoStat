package com.mikyegresl.valostat.base.network.model.agent

import com.google.gson.annotations.SerializedName

data class AgentsResponse(
    @SerializedName("status")
    val status: Int? = null,
    @SerializedName("data")
    val data: List<AgentResponse>? = null
)