package com.mikyegresl.valostat.base.network.model.weapon.skin

import com.squareup.moshi.Json

data class WeaponSkinLevelResponse(
    @Json(name = "uuid")
    val uuid: String? = null,
    @Json(name = "displayName")
    val name: String? = null,
    @Json(name = "assetPath")
    val assetPath: String? = null,
    @Json(name = "displayIcon")
    val iconPath: String? = null,

    //TODO: enum
    @Json(name = "levelItem")
    val levelItem: String? = null,

    @Json(name = "streamedVideo")
    val streamedVideo: String? = null
)