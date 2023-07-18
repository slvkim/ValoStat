package com.mikyegresl.valostat.features.agent

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mikyegresl.valostat.navigation.GlobalNavItem
import com.mikyegresl.valostat.navigation.NavigationItem
import kotlinx.coroutines.flow.StateFlow

fun agentsRouter(
    state: StateFlow<AgentsScreenState>,
    navController: NavController,
    builder: NavGraphBuilder
 ) {
    with(builder) {
        composable(GlobalNavItem.Agents.route) {
            AgentsScreen(state) {
                navController.navigate("${NavigationItem.AgentDetails.route}/$it") {
                    navController.graph.startDestinationRoute?.let { screenRoute ->
                        popUpTo(screenRoute)
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    }
}