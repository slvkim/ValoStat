package com.mikyegresl.valostat.base.network.model.weapon.stats

import com.google.gson.annotations.SerializedName

data class WeaponAdsStatsResponse(
    @SerializedName("burstCount")
    val burstCount: Int? = null,
    @SerializedName("fireRate")
    val fireRate: Double? = null,
    @SerializedName("firstBulletAccuracy")
    val firstBulletAccuracy: Double? = null,
    @SerializedName("runSpeedMultiplier")
    val runSpeedMultiplier: Double? = null,
    @SerializedName("zoomMultiplier")
    val zoomMultiplier: Double? = null
)