package com.mikyegresl.valostat.base.network.model.weapon.skin

import com.google.gson.annotations.SerializedName

data class WeaponSkinLevelResponse(
    @SerializedName("uuid")
    val uuid: String? = null,
    @SerializedName("displayName")
    val name: String? = null,
    @SerializedName("assetPath")
    val assetPath: String? = null,
    @SerializedName("displayIcon")
    val iconPath: String? = null,
    @SerializedName("levelItem")
    val levelItem: String? = null,
    @SerializedName("streamedVideo")
    val streamedVideo: String? = null
) {
    enum class SkinLevelResponse {
        //TODO: add level item enums
//    "EEquippableSkinLevelItem::HeartbeatAndMapSensor",
//    "EEquippableSkinLevelItem::VFX",
//    "EEquippableSkinLevelItem::Finisher",
//    "EEquippableSkinLevelItem::Animation",
//    "EEquippableSkinLevelItem::KillEffect",
//    "EEquippableSkinLevelItem::SoundEffects",
//    "EEquippableSkinLevelItem::Voiceover",
//    "EEquippableSkinLevelItem::TopFrag",
    }
}