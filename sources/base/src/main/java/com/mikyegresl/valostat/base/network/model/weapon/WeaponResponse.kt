package com.mikyegresl.valostat.base.network.model.weapon

import com.google.gson.annotations.SerializedName
import com.mikyegresl.valostat.base.network.model.weapon.shop.WeaponShopDataResponse
import com.mikyegresl.valostat.base.network.model.weapon.skin.WeaponSkinResponse
import com.mikyegresl.valostat.base.network.model.weapon.stats.WeaponStatsResponse

data class WeaponResponse(
    @SerializedName("uuid")
    val uuid: String? = null,
    @SerializedName("displayName")
    val displayName: String? = null,
    @SerializedName("category")
    val category: WeaponCategoryResponse? = null,
    @SerializedName("displayIcon")
    val displayIcon: String? = null,
    @SerializedName("weaponStats")
    val stats: WeaponStatsResponse? = null,
    @SerializedName("skins")
    val skins: List<WeaponSkinResponse>? = null,
    @SerializedName("shopData")
    val shopData: WeaponShopDataResponse? = null
) {
    enum class WeaponCategoryResponse {
        @SerializedName("EEquippableCategory::Rifle")
        RIFLE,
        @SerializedName("EEquippableCategory::Heavy")
        HEAVY,
        @SerializedName("EEquippableCategory::Shotgun")
        SHOTGUN,
        @SerializedName("EEquippableCategory::Sidearm")
        SIDEARM,
        @SerializedName("EEquippableCategory::Sniper")
        SNIPER,
        @SerializedName("EEquippableCategory::SMG")
        SMG,
        @SerializedName("EEquippableCategory::Melee")
        MELEE
    }
}