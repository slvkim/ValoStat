package com.mikyegresl.valostat.base.network.model.weapon.shop

import com.google.gson.annotations.SerializedName

data class WeaponShopDataResponse(
    @SerializedName("categoryText")
    val categoryText: String? = null,
    @SerializedName("cost")
    val cost: Int? = null,
    @SerializedName("gridPosition")
    val gridPosition: WeaponShopGridPositionResponse? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("newImage")
    val newImage: String? = null,
    @SerializedName("newImage2")
    val newImage2: String? = null,
    @SerializedName("assetPath")
    val assetPath: String? = null,
    @SerializedName("canBeTrashed")
    val canBeTrashed: Boolean? = null,

    //TODO: enum
    @SerializedName("category")
    val category: String? = null,
)