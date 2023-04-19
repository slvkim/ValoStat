package com.mikyegresl.valostat.base.model.weapon.skin

data class WeaponSkinDto(
    val uuid: String,
    val themeUuid: String,
    val contentTierUuid: String,
    val name: String,
    val iconPath: String,
    val wallpaperPath: String,
    val assetPath: String,
    val chromas: List<WeaponSkinChromaDto>,
    val levels: List<WeaponSkinLevelDto>
)