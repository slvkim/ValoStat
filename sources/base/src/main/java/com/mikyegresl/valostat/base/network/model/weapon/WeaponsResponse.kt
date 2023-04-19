package com.mikyegresl.valostat.base.network.model.weapon

import com.squareup.moshi.Json

data class WeaponsResponse(
    @Json(name = "status")
    val status: Int? = null,
    @Json(name = "data")
    val data: List<WeaponResponse>? = null
)