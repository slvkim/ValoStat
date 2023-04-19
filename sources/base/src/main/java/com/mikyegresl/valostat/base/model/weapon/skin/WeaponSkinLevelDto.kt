package com.mikyegresl.valostat.base.model.weapon.skin

data class WeaponSkinLevelDto(
    val uuid: String,
    val name: String,
    val assetPath: String,
    val iconPath: String,

    //TODO: enum
    val levelItem: String,

    val streamedVideo: String
)