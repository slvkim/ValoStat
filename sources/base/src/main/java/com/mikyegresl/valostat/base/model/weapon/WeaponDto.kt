package com.mikyegresl.valostat.base.model.weapon

import android.os.Parcelable
import com.mikyegresl.valostat.base.model.weapon.shop.WeaponShopDataDto
import com.mikyegresl.valostat.base.model.weapon.skin.WeaponSkinDto
import com.mikyegresl.valostat.base.model.weapon.stats.WeaponStatsDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeaponDto(
    val uuid: String,
    val displayName: String,
    val displayIcon: String,
    val category: WeaponCategoryDto,
    val stats: WeaponStatsDto,
    val skins: List<WeaponSkinDto>,
    val shopData: WeaponShopDataDto
) : Parcelable {

    @Parcelize
    enum class WeaponCategoryDto(val title: String) : Parcelable {
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