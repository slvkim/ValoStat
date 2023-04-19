package com.mikyegresl.valostat.base.network.model.weapon.shop

import com.squareup.moshi.Json

data class WeaponShopGridPositionResponse(
    @Json(name = "column")
    val column: Int? = null,
    @Json(name = "row")
    val row: Int? = null
)