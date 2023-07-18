package com.mikyegresl.valostat.features.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mikyegresl.valostat.base.model.ValoStatLocale
import com.mikyegresl.valostat.navigation.GlobalNavItem

fun settingsRouter(
    locales: Map<String, ValoStatLocale>,
    actions: SettingsScreenActions,
    builder: NavGraphBuilder
) {
    with(builder) {
        composable(GlobalNavItem.Settings.route) {
            SettingsScreen(
                locales = locales,
                actions = actions
            )
        }
    }
}