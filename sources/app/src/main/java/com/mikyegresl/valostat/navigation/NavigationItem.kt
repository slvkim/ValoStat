package com.mikyegresl.valostat.navigation

import androidx.annotation.StringRes
import com.mikyegresl.valostat.R

sealed class NavigationItem(
    @StringRes val title: Int,
    val route: String
) {
    object AgentDetails : NavigationItem(
        title = R.string.agent_details,
        route = "agentDetails"
    ) {
        const val agentId = "agentId"
    }

    object WeaponDetails: NavigationItem(
        title = R.string.weapon_details,
        route = "weaponDetails"
    ) {
        const val weaponId = "weaponId"
    }

    object NewsDetails : NavigationItem(
        title = R.string.news_details,
        route = "newsDetails"
    ) {
        const val url = "url"
    }
}


