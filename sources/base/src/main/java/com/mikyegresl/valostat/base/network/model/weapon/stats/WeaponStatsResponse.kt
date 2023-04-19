package com.mikyegresl.valostat.base.network.model.weapon.stats

import com.squareup.moshi.Json

data class WeaponStatsResponse(
    @Json(name = "equipTimeSeconds")
    val equipTimeSeconds: Double? = null,
    @Json(name = "fireRate")
    val fireRate: Double? = null,
    @Json(name = "firstBulletAccuracy")
    val firstBulletAccuracy: Double? = null,
    @Json(name = "magazineSize")
    val magazineSize: Int? = null,
    @Json(name = "reloadTimeSeconds")
    val reloadTimeSeconds: Double? = null,
    @Json(name = "runSpeedMultiplier")
    val runSpeedMultiplier: Double? = null,
    @Json(name = "shotgunPelletCount")
    val shotgunPelletCount: Int? = null,
    @Json(name = "adsStats")
    val adsStats: WeaponAdsStatsResponse? = null,
    @Json(name = "airBurstStats")
    val airBurstStats: WeaponAirBurstStatsResponse? = null,
    @Json(name = "altShotgunStats")
    val altShotgunStats: WeaponAltShotgunStatsResponse? = null,
    @Json(name = "damageRanges")
    val damageRanges: List<WeaponDamageRangeResponse>? = null,

    //TODO: enum
    @Json(name = "altFireType")
    val altFireType: String? = null,

    //TODO: enum
    @Json(name = "feature")
    val feature: String? = null,

    //TODO: enum
    @Json(name = "fireMode")
    val fireMode: String? = null,

    //TODO: enum
    @Json(name = "wallPenetration")
    val wallPenetration: String? = null
)