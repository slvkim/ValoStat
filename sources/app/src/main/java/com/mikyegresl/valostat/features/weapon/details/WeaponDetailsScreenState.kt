package com.mikyegresl.valostat.features.weapon.details

import com.mikyegresl.valostat.base.model.weapon.WeaponDto
import com.mikyegresl.valostat.base.model.weapon.skin.WeaponSkinChromaDto
import com.mikyegresl.valostat.common.state.BaseState

sealed class WeaponDetailsScreenState : BaseState {

    object WeaponDetailsLoadingState: WeaponDetailsScreenState()

    data class WeaponDetailsErrorState(val t: Throwable) : WeaponDetailsScreenState()

    data class WeaponDetailsDataState(
        val details: WeaponDto,
        val activeChroma: WeaponSkinChromaDto? = null
    ) : WeaponDetailsScreenState() {
        val skinsWithVideo: List<WeaponSkinChromaDto> get() {
            val list = mutableListOf<WeaponSkinChromaDto>()

            details.skins.forEach { skin ->
                skin.chromas.firstOrNull { chroma ->
                    chroma.streamedVideo.isNotEmpty()
                }?.let {
                    list.add(
                        it.copy(
                            iconPath = skin.iconPath
                        )
                    )
                }
            }
            return list
        }
    }
}