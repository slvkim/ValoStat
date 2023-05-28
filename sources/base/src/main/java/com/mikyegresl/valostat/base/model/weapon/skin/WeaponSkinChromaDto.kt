package com.mikyegresl.valostat.base.model.weapon.skin

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeaponSkinChromaDto(
    val uuid: String,
    val displayName: String,
    val assetPath: String,
    val iconPath: String,
    val fullRenderPath: String,
    val swatchPath: String,
    val streamedVideo: String
) : Parcelable