package com.mikyegresl.valostat.features.settings

import com.mikyegresl.valostat.base.model.ValoStatLocale
import com.mikyegresl.valostat.common.state.BaseState

sealed class SettingsScreenState: BaseState {

    object SettingsLoadingState: SettingsScreenState()

    data class SettingsDataState(
        val locales: Map<String, ValoStatLocale>,
        val currentLocaleKey: String
    ) : SettingsScreenState()
}