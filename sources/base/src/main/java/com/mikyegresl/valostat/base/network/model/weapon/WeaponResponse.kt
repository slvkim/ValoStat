package com.mikyegresl.valostat.base.network.model.weapon

import com.mikyegresl.valostat.base.network.model.weapon.shop.WeaponShopDataResponse
import com.mikyegresl.valostat.base.network.model.weapon.skin.WeaponSkinResponse
import com.mikyegresl.valostat.base.network.model.weapon.stats.WeaponStatsResponse
import com.squareup.moshi.Json

data class WeaponResponse(
    @Json(name = "uuid")
    val uuid: String? = null,
    @Json(name = "displayName")
    val name: String? = null,

    //TODO: enum
    @Json(name = "category")
    val category: String? = null,

    @Json(name = "assetPath")
    val assetPath: String? = null,
    @Json(name = "displayIcon")
    val iconPath: String? = null,
    @Json(name = "killStreamIcon")
    val killStreamIcon: String? = null,
    @Json(name = "weaponStats")
    val stats: WeaponStatsResponse? = null,
    @Json(name = "skins")
    val skins: List<WeaponSkinResponse>? = null,
    @Json(name = "shopData")
    val shopData: WeaponShopDataResponse? = null
)