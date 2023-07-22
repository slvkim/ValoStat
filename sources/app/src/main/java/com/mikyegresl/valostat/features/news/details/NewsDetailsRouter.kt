package com.mikyegresl.valostat.features.news.details

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mikyegresl.valostat.base.model.ValoStatLocale
import com.mikyegresl.valostat.navigation.NavigationItem
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

fun newsDetailsRouter(
    currentLocale: ValoStatLocale,
    navController: NavController,
    builder: NavGraphBuilder
) {
    with(builder) {
        composable(
            route = "${NavigationItem.NewsDetails.route}/{${NavigationItem.NewsDetails.url}}",
            arguments = listOf(
                navArgument(NavigationItem.NewsDetails.url) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.getString(NavigationItem.NewsDetails.url)?.let { url ->
                NewsDetailsScreen(
                    url = URLDecoder.decode(url, StandardCharsets.UTF_8.toString()),
                    locale = currentLocale
                ) {
                    navController.navigateUp()
                }
            }
        }
    }
}