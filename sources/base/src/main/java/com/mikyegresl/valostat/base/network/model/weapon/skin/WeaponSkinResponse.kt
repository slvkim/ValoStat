package com.mikyegresl.valostat.base.network.model.weapon.skin

import com.squareup.moshi.Json

data class WeaponSkinResponse(
    @Json(name = "uuid")
    val uuid: String? = null,
    @Json(name = "displayName")
    val name: String? = null,
    @Json(name = "displayIcon")
    val iconPath: String? = null,
    @Json(name = "wallpaper")
    val wallpaperPath: String? = null,
    @Json(name = "assetPath")
    val assetPath: String? = null,
    @Json(name = "themeUuid")
    val themeUuid: String? = null,
    @Json(name = "contentTierUuid")
    val contentTierUuid: String? = null,
    @Json(name = "chromas")
    val chromas: List<WeaponSkinChromaResponse>? = null,
    @Json(name = "levels")
    val levels: List<WeaponSkinLevelResponse>? = null
)