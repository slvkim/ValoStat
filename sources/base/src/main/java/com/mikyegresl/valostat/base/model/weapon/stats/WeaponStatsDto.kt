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
    val wallPenetration: WallPenetrationDto,
    val feature: WeaponFeatureDto,
    val fireMode: FireModeDto,
    val altFireType: AltFireTypeDto
) {
    enum class WallPenetrationDto(val title: String) {
        HIGH("High"),
        MEDIUM("Medium"),
        LOW("Low"),
        UNKNOWN("Unknown")
    }

    enum class WeaponFeatureDto(val title: String) {
        SILENCED("Silenced"),
        DUAL_ZOOM("Dual Zoom"),
        ROF_INCREASE("ROF Increase"),
        UNKNOWN("Unknown")
    }

    enum class FireModeDto(val title: String) {
        SEMI_AUTOMATIC("Semi Automatic"),
        UNKNOWN("Unknown")
    }

    enum class AltFireTypeDto(val title: String) {
        ADS("ADS"),
        AIR_BURST("Air Burst"),
        SHOTGUN("Shotgun"),
        UNKNOWN("Unknown")
    }
}