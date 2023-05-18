package com.mikyegresl.valostat.base.network.model.weapon.stats

import com.google.gson.annotations.SerializedName

data class WeaponDamageRangeResponse(
    @SerializedName("bodyDamage")
    val bodyDamage: Int? = null,
    @SerializedName("headDamage")
    val headDamage: Double? = null,
    @SerializedName("legDamage")
    val legDamage: Double? = null,
    @SerializedName("rangeEndMeters")
    val rangeEndMeters: Int? = null,
    @SerializedName("rangeStartMeters")
    val rangeStartMeters: Int? = null
)