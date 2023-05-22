package com.mikyegresl.valostat.base.model.weapon.skin

data class WeaponSkinDto(
    val uuid: String,
    val themeUuid: String,
    val contentTierUuid: String,
    val displayName: String,
    val iconPath: String,
    val chromas: List<WeaponSkinChromaDto>,
    val levels: List<WeaponSkinLevelDto>
)