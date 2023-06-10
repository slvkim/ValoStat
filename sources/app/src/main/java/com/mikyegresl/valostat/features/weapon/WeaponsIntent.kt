package com.mikyegresl.valostat.features.weapon

import com.mikyegresl.valostat.base.model.ValoStatLocale

sealed class WeaponsIntent {

    data class UpdateWeaponsIntent(
        val locale: ValoStatLocale
    ) : WeaponsIntent()
}