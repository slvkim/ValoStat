package com.mikyegresl.valostat.base.model.weapon.stats

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeaponDamageRangeDto(
    val bodyDamage: Int,
    val headDamage: Double,
    val legDamage: Double,
    val rangeEndMeters: Int,
    val rangeStartMeters: Int
) : Parcelable