package com.mikyegresl.valostat.features.weapon.details

import com.mikyegresl.valostat.base.model.weapon.WeaponDto
import com.mikyegresl.valostat.base.model.weapon.skin.WeaponSkinChromaDto

data class WeaponDetailsScreenState(
    val details: WeaponDto
) {
    val skinsWithVideo: List<WeaponSkinChromaDto> get() {
        val list = mutableListOf<WeaponSkinChromaDto>()

        details.skins.forEach { skin ->
            skin.chromas.firstOrNull {
                it.streamedVideo.isNotEmpty()
            }?.let {
                list.add(it)
            }
        }
        return list
    }
}