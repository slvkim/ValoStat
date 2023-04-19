package com.mikyegresl.valostat.base.model.weapon.shop

data class WeaponShopDataDto(
    val categoryText: String,
    val cost: Int,
    val gridPosition: WeaponShopGridPositionDto,
    val image: String,
    val newImage: String,
    val newImage2: String,
    val assetPath: String,
    val canBeTrashed: Boolean,

    //TODO: enum
    val category: String,
)