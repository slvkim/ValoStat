package com.mikyegresl.valostat.base.model.weapon.stats

data class WeaponStatsDto(
    val equipTimeSeconds: Double,
    val fireRate: Double,
    val firstBulletAccuracy: Double,
    val magazineSize: Int,
    val reloadTimeSeconds: Double,
    val runSpeedMultiplier: Double,
    val shotgunPelletCount: Int,
    val adsStats: WeaponAdsStatsDto,
    val airBurstStats: WeaponAirBurstStatsDto?,
    val altShotgunStats: WeaponAltShotgunStatsDto?,
    val damageRanges: List<WeaponDamageRangeDto>,

    //TODO: enum
    val altFireType: String,

    //TODO: enum
    val feature: String,

    //TODO: enum
    val fireMode: String,

    //TODO: enum
    val wallPenetration: String
)