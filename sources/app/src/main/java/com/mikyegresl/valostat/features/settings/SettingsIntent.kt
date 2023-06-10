package com.mikyegresl.valostat.features.settings

sealed class SettingsIntent {
    object AppLocaleChangedIntent: SettingsIntent()
}
