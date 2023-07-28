package com.mikyegresl.valostat.features.news.details

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mikyegresl.valostat.base.model.ValoStatLocale
import com.mikyegresl.valostat.base.model.news.NewsNavigationEncoder
import com.mikyegresl.valostat.navigation.NavigationItem
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

fun newsDetailsRouter(
    currentLocale: ValoStatLocale,
    navController: NavController,
    builder: NavGraphBuilder,
    decoder: NewsNavigationEncoder
) {
    with(builder) {
        composable(
            route = "${NavigationItem.NewsDetails.route}/{${NavigationItem.NewsDetails.article}}",
            arguments = listOf(
                navArgument(NavigationItem.NewsDetails.article) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.getString(NavigationItem.NewsDetails.article)?.let {
                val article = decoder.decode(URLDecoder.decode(it, StandardCharsets.UTF_8.toString()))
                NewsDetailsScreen(
                    singleArticle = article,
                    locale = currentLocale
                ) {
                    navController.navigateUp()
                }
            }
        }
    }
}