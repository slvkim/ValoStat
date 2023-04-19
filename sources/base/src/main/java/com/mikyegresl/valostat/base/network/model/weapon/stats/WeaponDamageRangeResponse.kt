package com.mikyegresl.valostat.base.network.model.weapon.stats

import com.squareup.moshi.Json

data class WeaponDamageRangeResponse(
    @Json(name = "bodyDamage")
    val bodyDamage: Int? = null,
    @Json(name = "headDamage")
    val headDamage: Double? = null,
    @Json(name = "legDamage")
    val legDamage: Double? = null,
    @Json(name = "rangeEndMeters")
    val rangeEndMeters: Int? = null,
    @Json(name = "rangeStartMeters")
    val rangeStartMeters: Int? = null
)