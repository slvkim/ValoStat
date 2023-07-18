package com.mikyegresl.valostat.features.weapon.details

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mikyegresl.valostat.base.model.ValoStatLocale
import com.mikyegresl.valostat.navigation.NavigationItem
import kotlinx.coroutines.flow.StateFlow

fun weaponDetailsRouter(
    currentLocale: ValoStatLocale,
    actions: WeaponDetailsScreenActions,
    state: StateFlow<WeaponDetailsScreenState>,
    builder: NavGraphBuilder
) {
    with(builder) {
        composable(
            route = "${NavigationItem.WeaponDetails.route}/{${NavigationItem.WeaponDetails.weaponId}}",
            arguments = listOf(
                navArgument(NavigationItem.WeaponDetails.weaponId) {
                    type = NavType.StringType
                }
            )
        ) {
            it.arguments?.getString(NavigationItem.WeaponDetails.weaponId)?.let { weaponId ->
                WeaponDetailsScreen(
                    weaponId = weaponId,
                    locale = currentLocale,
                    state = state,
                    actions = actions,
                )
            }
        }
    }
}