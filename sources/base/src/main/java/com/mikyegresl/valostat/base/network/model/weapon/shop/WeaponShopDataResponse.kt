package com.mikyegresl.valostat.base.network.model.weapon.shop

import com.squareup.moshi.Json

data class WeaponShopDataResponse(
    @Json(name = "categoryText")
    val categoryText: String? = null,
    @Json(name = "cost")
    val cost: Int? = null,
    @Json(name = "gridPosition")
    val gridPosition: WeaponShopGridPositionResponse? = null,
    @Json(name = "image")
    val image: String? = null,
    @Json(name = "newImage")
    val newImage: String? = null,
    @Json(name = "newImage2")
    val newImage2: String? = null,
    @Json(name = "assetPath")
    val assetPath: String? = null,
    @Json(name = "canBeTrashed")
    val canBeTrashed: Boolean? = null,

    //TODO: enum
    @Json(name = "category")
    val category: String? = null,
)