package com.mikyegresl.valostat.base.network.model.weapon.stats

import com.squareup.moshi.Json

data class WeaponAirBurstStatsResponse(
    @Json(name = "burstDistance")
    val burstDistance: Double? = null,
    @Json(name = "shotgunPelletCount")
    val shotgunPelletCount: Int? = null
)