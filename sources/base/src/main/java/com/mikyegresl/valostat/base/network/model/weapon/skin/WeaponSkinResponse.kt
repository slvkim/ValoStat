package com.mikyegresl.valostat.base.network.model.weapon.skin

import com.google.gson.annotations.SerializedName

data class WeaponSkinResponse(
    @SerializedName("uuid")
    val uuid: String? = null,
    @SerializedName("displayName")
    val name: String? = null,
    @SerializedName("displayIcon")
    val iconPath: String? = null,
    @SerializedName("wallpaper")
    val wallpaperPath: String? = null,
    @SerializedName("assetPath")
    val assetPath: String? = null,
    @SerializedName("themeUuid")
    val themeUuid: String? = null,
    @SerializedName("contentTierUuid")
    val contentTierUuid: String? = null,
    @SerializedName("chromas")
    val chromas: List<WeaponSkinChromaResponse>? = null,
    @SerializedName("levels")
    val levels: List<WeaponSkinLevelResponse>? = null
)