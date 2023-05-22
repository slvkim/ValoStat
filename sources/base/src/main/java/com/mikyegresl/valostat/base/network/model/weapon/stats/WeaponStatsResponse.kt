package com.mikyegresl.valostat.base.network.model.weapon.stats

import com.google.gson.annotations.SerializedName

data class WeaponStatsResponse(
    @SerializedName("equipTimeSeconds")
    val equipTimeSeconds: Double? = null,
    @SerializedName("fireRate")
    val fireRate: Double? = null,
    @SerializedName("firstBulletAccuracy")
    val firstBulletAccuracy: Double? = null,
    @SerializedName("magazineSize")
    val magazineSize: Int? = null,
    @SerializedName("reloadTimeSeconds")
    val reloadTimeSeconds: Double? = null,
    @SerializedName("runSpeedMultiplier")
    val runSpeedMultiplier: Double? = null,
    @SerializedName("shotgunPelletCount")
    val shotgunPelletCount: Int? = null,
    @SerializedName("adsStats")
    val adsStats: WeaponAdsStatsResponse? = null,
    @SerializedName("airBurstStats")
    val airBurstStats: WeaponAirBurstStatsResponse? = null,
    @SerializedName("altShotgunStats")
    val altShotgunStats: WeaponAltShotgunStatsResponse? = null,
    @SerializedName("damageRanges")
    val damageRanges: List<WeaponDamageRangeResponse>? = null,
    @SerializedName("wallPenetration")
    val wallPenetration: WallPenetrationResponse? = null,
    @SerializedName("feature")
    val feature: WeaponFeatureResponse? = null,
    @SerializedName("fireMode")
    val fireMode: FireModeResponse? = null,
    @SerializedName("altFireType")
    val altFireType: AltFireTypeResponse? = null
) {
    enum class WallPenetrationResponse {
        @SerializedName("EWallPenetrationDisplayType::Low")
        LOW,
        @SerializedName("EWallPenetrationDisplayType::Medium")
        MEDIUM,
        @SerializedName("EWallPenetrationDisplayType::High")
        HIGH
    }

    enum class WeaponFeatureResponse {
        @SerializedName("EWeaponStatsFeature::Silenced")
        SILENCED,
        @SerializedName("EWeaponStatsFeature::DualZoom")
        DUAL_ZOOM,
        @SerializedName("EWeaponStatsFeature::ROFIncrease")
        ROF_INCREASE
    }

    enum class FireModeResponse {
        @SerializedName("EWeaponFireModeDisplayType::SemiAutomatic")
        SEMI_AUTOMATIC
    }

    enum class AltFireTypeResponse {
        @SerializedName("EWeaponAltFireDisplayType::ADS")
        ADS,
        @SerializedName("EWeaponAltFireDisplayType::AirBurst")
        AIR_BURST,
        @SerializedName("EWeaponAltFireDisplayType::Shotgun")
        SHOTGUN
    }
}