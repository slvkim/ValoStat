package com.mikyegresl.valostat.base.network.model.weapon.stats

import com.google.gson.annotations.SerializedName

data class WeaponAltShotgunStatsResponse(
    @SerializedName("burstRate")
    val burstRate: Double? = null,
    @SerializedName("burstDistance")
    val shotgunPelletCount: Int? = null
)