package com.mikyegresl.valostat.base.network.model.agent

import com.google.gson.annotations.SerializedName

data class AgentAbilityResponse(
    @SerializedName("displayName")
    val displayName: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("displayIcon")
    val displayIcon: String? = null,
    @SerializedName("slot")
    val slot: String? = null
)