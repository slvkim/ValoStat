package com.mikyegresl.valostat.base.model.weapon

import android.os.Parcelable
import androidx.annotation.StringRes
import com.mikyegresl.valostat.base.R
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
    enum class WeaponCategoryDto(@StringRes val titleRes: Int) : Parcelable {
        RIFLE(R.string.rifle),
        HEAVY(R.string.heavy),
        SHOTGUN(R.string.shotgun),
        SIDEARM(R.string.sidearm),
        SNIPER(R.string.sniper),
        SMG(R.string.smg),
        MELEE(R.string.melee),
        UNKNOWN(R.string.unknown)
    }
}