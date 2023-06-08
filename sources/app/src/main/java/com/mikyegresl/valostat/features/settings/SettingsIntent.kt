package com.mikyegresl.valostat.features.settings

import com.mikyegresl.valostat.base.model.ValoStatLocale

sealed class SettingsIntent {
    data class AppLocaleChangedIntent(
        val locale: ValoStatLocale
    ) : SettingsIntent()
}
