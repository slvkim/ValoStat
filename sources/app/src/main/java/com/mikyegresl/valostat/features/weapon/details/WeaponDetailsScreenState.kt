package com.mikyegresl.valostat.features.weapon.details

import com.mikyegresl.valostat.base.model.weapon.WeaponDto
import com.mikyegresl.valostat.base.model.weapon.skin.WeaponSkinChromaDto
import com.mikyegresl.valostat.base.model.weapon.skin.WeaponSkinDto
import com.mikyegresl.valostat.common.state.BaseState
import com.mikyegresl.valostat.features.player.exoplayer.ExoPlayerConfig

sealed class WeaponDetailsScreenState : BaseState {

    object WeaponDetailsLoadingState: WeaponDetailsScreenState()

    data class WeaponDetailsErrorState(val t: Throwable) : WeaponDetailsScreenState()

    sealed class WeaponDetailsInGeneralState(
        open val details: WeaponDto,
        open val activeVideoChroma: WeaponSkinChromaDto? = null,
        open val playerConfig: ExoPlayerConfig,
        open val continuePlayback: Boolean
    ): WeaponDetailsScreenState() {

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

        data class WeaponDetailsInDataState(
            override val details: WeaponDto,
            override val activeVideoChroma: WeaponSkinChromaDto? = null,
            override val playerConfig: ExoPlayerConfig,
            override val continuePlayback: Boolean
        ) : WeaponDetailsInGeneralState(details, activeVideoChroma, playerConfig, continuePlayback)

        data class WeaponDetailsInFullscreenState(
            override val details: WeaponDto,
            override val activeVideoChroma: WeaponSkinChromaDto,
            override val playerConfig: ExoPlayerConfig,
            override val continuePlayback: Boolean
        ): WeaponDetailsInGeneralState(details, activeVideoChroma, playerConfig, continuePlayback)
    }
}