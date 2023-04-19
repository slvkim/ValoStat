package com.mikyegresl.valostat.base.network.model.weapon.stats

import com.squareup.moshi.Json

data class WeaponAdsStatsResponse(
    @Json(name = "burstCount")
    val burstCount: Int? = null,
    @Json(name = "fireRate")
    val fireRate: Double? = null,
    @Json(name = "firstBulletAccuracy")
    val firstBulletAccuracy: Double? = null,
    @Json(name = "runSpeedMultiplier")
    val runSpeedMultiplier: Double? = null,
    @Json(name = "zoomMultiplier")
    val zoomMultiplier: Double? = null
)