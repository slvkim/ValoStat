package com.mikyegresl.valostat.base.network.model.weapon.stats

import com.squareup.moshi.Json

data class WeaponAltShotgunStatsResponse(
    @Json(name = "burstDistance")
    val burstRate: Double? = null,
    @Json(name = "burstDistance")
    val shotgunPelletCount: Int? = null
)