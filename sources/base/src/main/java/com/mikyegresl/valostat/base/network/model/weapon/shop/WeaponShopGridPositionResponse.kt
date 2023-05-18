package com.mikyegresl.valostat.base.network.model.weapon.shop

import com.google.gson.annotations.SerializedName

data class WeaponShopGridPositionResponse(
    @SerializedName("column")
    val column: Int? = null,
    @SerializedName("row")
    val row: Int? = null
)