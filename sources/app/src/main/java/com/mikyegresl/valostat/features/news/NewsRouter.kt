package com.mikyegresl.valostat.features.news

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mikyegresl.valostat.base.model.ValoStatLocale
import com.mikyegresl.valostat.navigation.GlobalNavItem
import kotlinx.coroutines.flow.StateFlow

fun newsRouter(
    state: StateFlow<NewsScreenState>,
    actions: NewsScreenActions,
    builder: NavGraphBuilder
) {
    with(builder) {
        composable(GlobalNavItem.News.route) {
            NewsScreen(
                screenState = state,
                actions = actions
            )
        }
    }
}