package com.mikyegresl.valostat.base.model.weapon

import com.mikyegresl.valostat.base.model.weapon.shop.WeaponShopDataDto
import com.mikyegresl.valostat.base.model.weapon.skin.WeaponSkinDto
import com.mikyegresl.valostat.base.model.weapon.stats.WeaponStatsDto

data class WeaponDto(
    val uuid: String,
    val name: String,

    //TODO: enum
    val category: String,

    val assetPath: String,
    val iconPath: String,
    val killStreamIcon: String,
    val stats: WeaponStatsDto,
    val skins: List<WeaponSkinDto>,
    val shopData: WeaponShopDataDto
)