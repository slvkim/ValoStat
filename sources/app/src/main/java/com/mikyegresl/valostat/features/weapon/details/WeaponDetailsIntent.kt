package com.mikyegresl.valostat.features.weapon.details

import com.mikyegresl.valostat.base.model.weapon.skin.WeaponSkinChromaDto

sealed class WeaponDetailsIntent {

    data class VideoClickedIntent(
        val chroma: WeaponSkinChromaDto
    ) : WeaponDetailsIntent()

    data class VideoDisposeIntent(
        val chroma: WeaponSkinChromaDto
    ) : WeaponDetailsIntent()

    data class RefreshWeaponDetailsIntent(
        val weaponId: String
    ) : WeaponDetailsIntent()
}