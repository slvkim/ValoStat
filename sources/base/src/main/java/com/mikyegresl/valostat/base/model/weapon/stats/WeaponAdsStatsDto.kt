package com.mikyegresl.valostat.base.model.weapon.stats

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeaponAdsStatsDto(
    val burstCount: Int,
    val fireRate: Double,
    val firstBulletAccuracy: Double,
    val runSpeedMultiplier: Double,
    val zoomMultiplier: Double
) : Parcelable