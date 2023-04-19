package com.mikyegresl.valostat.base.model.weapon.stats

data class WeaponAdsStatsDto(
    val burstCount: Int,
    val fireRate: Double,
    val firstBulletAccuracy: Double,
    val runSpeedMultiplier: Double,
    val zoomMultiplier: Double
)