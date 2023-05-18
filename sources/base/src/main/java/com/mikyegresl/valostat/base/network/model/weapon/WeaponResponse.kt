package com.mikyegresl.valostat.base.network.model.weapon

import com.google.gson.annotations.SerializedName
import com.mikyegresl.valostat.base.network.model.weapon.shop.WeaponShopDataResponse
import com.mikyegresl.valostat.base.network.model.weapon.skin.WeaponSkinResponse
import com.mikyegresl.valostat.base.network.model.weapon.stats.WeaponStatsResponse

data class WeaponResponse(
    @SerializedName("uuid")
    val uuid: String? = null,
    @SerializedName("displayName")
    val name: String? = null,

    //TODO: enum
    @SerializedName("category")
    val category: String? = null,

    @SerializedName("assetPath")
    val assetPath: String? = null,
    @SerializedName("displayIcon")
    val iconPath: String? = null,
    @SerializedName("killStreamIcon")
    val killStreamIcon: String? = null,
    @SerializedName("weaponStats")
    val stats: WeaponStatsResponse? = null,
    @SerializedName("skins")
    val skins: List<WeaponSkinResponse>? = null,
    @SerializedName("shopData")
    val shopData: WeaponShopDataResponse? = null
)