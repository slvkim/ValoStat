package com.mikyegresl.valostat.base.network.model.weapon.skin

import com.squareup.moshi.Json

data class WeaponSkinChromaResponse(
    @Json(name = "uuid")
    val uuid: String? = null,
    @Json(name = "displayName")
    val name: String? = null,
    @Json(name = "assetPath")
    val assetPath: String? = null,
    @Json(name = "displayIcon")
    val iconPath: String? = null,
    @Json(name = "fullRender")
    val fullRenderPath: String? = null,
    @Json(name = "swatch")
    val swatchPath: String? = null,
    @Json(name = "streamedVideo")
    val streamedVideo: String? = null
)