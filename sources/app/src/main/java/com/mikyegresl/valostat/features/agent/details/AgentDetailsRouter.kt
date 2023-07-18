package com.mikyegresl.valostat.features.agent.details

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mikyegresl.valostat.base.model.ValoStatLocale
import com.mikyegresl.valostat.navigation.NavigationItem

fun agentDetailsRouter(
    currentLocale: ValoStatLocale,
    navController: NavController,
    builder: NavGraphBuilder
) {
    with(builder) {
        composable(
            route = "${NavigationItem.AgentDetails.route}/{${NavigationItem.AgentDetails.agentId}}",
            arguments = listOf(
                navArgument(NavigationItem.AgentDetails.agentId) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.getString(NavigationItem.AgentDetails.agentId)?.let { agentId ->
                AgentDetailsScreen(
                    agentId = agentId,
                    locale = currentLocale
                ) {
                    navController.navigateUp()
                }
            }
        }
    }
}