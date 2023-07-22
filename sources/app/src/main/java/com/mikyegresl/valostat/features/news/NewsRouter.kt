package com.mikyegresl.valostat.features.news

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mikyegresl.valostat.base.model.news.ArticleTypeDto
import com.mikyegresl.valostat.navigation.GlobalNavItem
import com.mikyegresl.valostat.navigation.NavigationItem
import kotlinx.coroutines.flow.StateFlow
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun newsRouter(
    state: StateFlow<NewsScreenState>,
    navController: NavController,
    builder: NavGraphBuilder,
    openWebPage: (String) -> Unit
) {
    with(builder) {
        composable(GlobalNavItem.News.route) {
            NewsScreen(
                screenState = state
            ) { articleUrl, articleType ->
                when (articleType) {
                    ArticleTypeDto.EXTERNAL_LINK -> {
                        openWebPage(articleUrl)
                    }
                    else -> {
                        val encodedUrl = URLEncoder.encode(articleUrl, StandardCharsets.UTF_8.toString())
                        val route = "${NavigationItem.NewsDetails.route}/$encodedUrl"
                        navController.navigate(route) {
                            navController.graph.startDestinationRoute?.let { screenRoute ->
                                popUpTo(GlobalNavItem.News.route)
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            }
        }
    }
}