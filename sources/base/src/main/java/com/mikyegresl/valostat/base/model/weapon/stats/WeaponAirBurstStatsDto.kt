package com.mikyegresl.valostat.base.model.weapon.stats

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeaponAirBurstStatsDto(
    val burstDistance: Double,
    val shotgunPelletCount: Int
) : Parcelable