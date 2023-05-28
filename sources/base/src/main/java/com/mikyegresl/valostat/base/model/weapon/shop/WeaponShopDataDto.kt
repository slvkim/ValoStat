package com.mikyegresl.valostat.base.model.weapon.shop

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
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
) : Parcelable