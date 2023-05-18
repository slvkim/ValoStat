package com.mikyegresl.valostat.base.network.model.weapon.skin

import com.google.gson.annotations.SerializedName

data class WeaponSkinLevelResponse(
    @SerializedName("uuid")
    val uuid: String? = null,
    @SerializedName("displayName")
    val name: String? = null,
    @SerializedName("assetPath")
    val assetPath: String? = null,
    @SerializedName("displayIcon")
    val iconPath: String? = null,

    //TODO: enum
    @SerializedName("levelItem")
    val levelItem: String? = null,

    @SerializedName("streamedVideo")
    val streamedVideo: String? = null
)