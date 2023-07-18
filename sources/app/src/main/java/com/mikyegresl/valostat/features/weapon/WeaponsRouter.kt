package com.mikyegresl.valostat.features.weapon

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mikyegresl.valostat.navigation.GlobalNavItem
import com.mikyegresl.valostat.navigation.NavigationItem
import kotlinx.coroutines.flow.StateFlow

fun weaponsRouter(
    state: StateFlow<WeaponsScreenState>,
    navController: NavController,
    builder: NavGraphBuilder
) {
    with(builder) {
        composable(GlobalNavItem.Weapons.route) {
            WeaponsScreen(state) {
                navController.navigate("${NavigationItem.WeaponDetails.route}/$it") {
                    navController.graph.startDestinationRoute?.let { screenRoute ->
                        popUpTo(GlobalNavItem.Weapons.route)
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    }
}