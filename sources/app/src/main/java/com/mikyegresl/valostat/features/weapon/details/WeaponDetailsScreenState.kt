package com.mikyegresl.valostat.features.weapon.details

import com.mikyegresl.valostat.base.model.weapon.WeaponDto
import com.mikyegresl.valostat.base.model.weapon.skin.WeaponSkinChromaDto
import com.mikyegresl.valostat.base.model.weapon.skin.WeaponSkinDto
import com.mikyegresl.valostat.common.state.BaseState

sealed class WeaponDetailsScreenState : BaseState {

    object WeaponDetailsLoadingState: WeaponDetailsScreenState()

    data class WeaponDetailsErrorState(val t: Throwable) : WeaponDetailsScreenState()

    data class WeaponDetailsDataState(
        val details: WeaponDto,
        val activeVideoChroma: WeaponSkinChromaDto? = null
    ) : WeaponDetailsScreenState() {

        val skinsWithChromas: List<WeaponSkinDto> get() =
            details.skins.filter { skin ->
                skin.chromas.size >= 2
            }.map {
                it.copy(iconPath = it.chromas.firstOrNull()?.fullRenderPath ?: it.iconPath)
            }

        val skinsWithVideo: List<WeaponSkinChromaDto> get() =
            details.skins.flatMap { skin ->
                skin.chromas.filter { it.streamedVideo.isNotEmpty() }
            }
    }
}