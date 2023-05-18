package com.mikyegresl.valostat.base.network.model.agent

import com.google.gson.annotations.SerializedName

data class AgentResponse(
    @SerializedName("uuid")
    val uuid: String? = null,
    @SerializedName("displayName")
    val displayName: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("background")
    val background: String? = null,
    @SerializedName("assetPath")
    val assetPath: String? = null,
    @SerializedName("displayIcon")
    val displayIcon: String? = null,
    @SerializedName("displayIconSmall")
    val displayIconSmall: String? = null,
    @SerializedName("fullPortrait")
    val fullPortrait: String? = null,
    @SerializedName("fullPortraitV2")
    val fullPortraitV2: String? = null,
    @SerializedName("killfeedPortrait")
    val killfeedPortrait: String? = null,
    @SerializedName("bustPortrait")
    val bustPortrait: String? = null,
    @SerializedName("developerName")
    val developerName: String? = null,
    @SerializedName("isAvailableForTest")
    val isAvailableForTest: Boolean? = null,
    @SerializedName("isBaseContent")
    val isBaseContent: Boolean? = null,
    @SerializedName("isFullPortraitRightFacing")
    val isFullPortraitRightFacing: Boolean? = null,
    @SerializedName("isPlayableCharacter")
    val isPlayableCharacter: Boolean? = null,
    @SerializedName("role")
    val role: AgentRoleResponse? = null,
    @SerializedName("voiceLine")
    val voiceLine: AgentVoiceLineResponse? = null,
    @SerializedName("abilities")
    val abilities: List<AgentAbilityResponse>? = null,
    @SerializedName("backgroundGradientColors")
    val backgroundGradientColors: List<String>? = null,
    @SerializedName("characterTags")
    val characterTags: List<String>? = null
)