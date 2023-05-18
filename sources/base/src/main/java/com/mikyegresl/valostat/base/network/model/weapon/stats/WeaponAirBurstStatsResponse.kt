package com.mikyegresl.valostat.base.network.model.weapon.stats

import com.google.gson.annotations.SerializedName

data class WeaponAirBurstStatsResponse(
    @SerializedName("burstDistance")
    val burstDistance: Double? = null,
    @SerializedName("shotgunPelletCount")
    val shotgunPelletCount: Int? = null
)