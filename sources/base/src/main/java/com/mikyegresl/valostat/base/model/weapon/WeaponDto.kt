package com.mikyegresl.valostat.base.model.weapon

import com.mikyegresl.valostat.base.model.weapon.shop.WeaponShopDataDto
import com.mikyegresl.valostat.base.model.weapon.skin.WeaponSkinDto
import com.mikyegresl.valostat.base.model.weapon.stats.WeaponStatsDto

data class WeaponDto(
    val uuid: String,
    val displayName: String,
    val displayIcon: String,
    val category: WeaponCategoryDto,
    val stats: WeaponStatsDto,
    val skins: List<WeaponSkinDto>,
    val shopData: WeaponShopDataDto
) {
    enum class WeaponCategoryDto(val title: String) {
        RIFLE("Rifle"),
        HEAVY("Heavy"),
        SHOTGUN("Shotgun"),
        SIDEARM("Sidearm"),
        SNIPER("Sniper"),
        SMG("SMG"),
        MELEE("Melee"),
        UNKNOWN("Unknown")
    }
}