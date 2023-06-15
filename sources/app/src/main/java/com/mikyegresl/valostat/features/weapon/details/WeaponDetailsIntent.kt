package com.mikyegresl.valostat.features.weapon.details

import com.mikyegresl.valostat.base.model.ValoStatLocale
import com.mikyegresl.valostat.base.model.weapon.skin.WeaponSkinChromaDto
import com.mikyegresl.valostat.features.player.exoplayer.ExoPlayerConfig

sealed class WeaponDetailsIntent {

    data class VideoClickedIntent(
        val chroma: WeaponSkinChromaDto
    ) : WeaponDetailsIntent()

    data class VideoDisposeIntent(
        val chroma: WeaponSkinChromaDto
    ) : WeaponDetailsIntent()

    data class UpdateWeaponDetailsIntent(
        val weaponId: String,
        val locale: ValoStatLocale
    ) : WeaponDetailsIntent()

    data class ContinueVideoPlaybackIntent(
        val playerConfig: ExoPlayerConfig
    ): WeaponDetailsIntent()
}