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

    //TODO: enum
    @SerializedName("altFireType")
    val altFireType: String? = null,

    //TODO: enum
    @SerializedName("feature")
    val feature: String? = null,

    //TODO: enum
    @SerializedName("fireMode")
    val fireMode: String? = null,

    //TODO: enum
    @SerializedName("wallPenetration")
    val wallPenetration: String? = null
)