package com.mikyegresl.valostat.features.news

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mikyegresl.valostat.base.model.news.ArticleTypeDto
import com.mikyegresl.valostat.base.model.news.NewsNavigationEncoder
import com.mikyegresl.valostat.navigation.GlobalNavItem
import com.mikyegresl.valostat.navigation.NavigationItem
import kotlinx.coroutines.flow.StateFlow
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun newsRouter(
    state: StateFlow<NewsScreenState>,
    navController: NavController,
    builder: NavGraphBuilder,
    encoder: NewsNavigationEncoder,
    openWebPage: (String) -> Unit
) {
    with(builder) {
        composable(GlobalNavItem.News.route) {
            NewsScreen(
                screenState = state
            ) { article ->
                if (article.type == ArticleTypeDto.EXTERNAL_LINK) {
                    openWebPage(article.externalLink)
                } else {
                    val encodedArticle = URLEncoder.encode(encoder.encode(article), StandardCharsets.UTF_8.toString())
                    val route = "${NavigationItem.NewsDetails.route}/$encodedArticle"
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