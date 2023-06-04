package com.mikyegresl.valostat.base.model.weapon.skin

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeaponSkinLevelDto(
    val uuid: String,
    val name: String,
    val assetPath: String,
    val iconPath: String,

    //TODO: enum
    val levelItem: String,

    val streamedVideo: String
) : Parcelable {

    @Parcelize
    enum class LevelItemDto(val title: String) : Parcelable {
        //TODO: add levelItem enum
    }
}
