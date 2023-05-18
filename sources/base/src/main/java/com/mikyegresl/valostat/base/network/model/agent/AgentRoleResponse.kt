package com.mikyegresl.valostat.base.network.model.agent

import com.google.gson.annotations.SerializedName

data class AgentRoleResponse(
    @SerializedName("uuid")
    val uuid: String? = null,
    @SerializedName("displayName")
    val displayName: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("assetPath")
    val assetPath: String? = null,
    @SerializedName("displayIcon")
    val displayIcon: String? = null
)