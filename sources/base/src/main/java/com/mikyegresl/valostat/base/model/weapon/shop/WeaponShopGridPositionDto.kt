package com.mikyegresl.valostat.base.model.weapon.shop

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeaponShopGridPositionDto(
    val column: Int,
    val row: Int
) : Parcelable