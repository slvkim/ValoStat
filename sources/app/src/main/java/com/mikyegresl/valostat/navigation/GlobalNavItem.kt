package com.mikyegresl.valostat.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.mikyegresl.valostat.R

sealed class GlobalNavItem(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: String
) {
    companion object {
        private const val AGENTS = "agents"
        private const val WEAPONS = "weapons"
        private const val MAPS = "maps"
        private const val VIDEOS = "videos"
        private const val SETTINGS = "settings"
    }

    object Agents : GlobalNavItem(
        title = R.string.agents,
        icon = R.drawable.ic_agents,
        route = AGENTS
    )

    object Weapons : GlobalNavItem(
        title = R.string.weapons,
        icon = R.drawable.ic_weapons,
        route = WEAPONS
    )

    object Maps : GlobalNavItem(
        title = R.string.maps,
        icon = R.drawable.ic_maps,
        route = MAPS
    )
    object Videos: GlobalNavItem(
        title = R.string.videos,
        icon = R.drawable.ic_videos,
        route = VIDEOS
    )

    object Settings: GlobalNavItem(
        title = R.string.settings,
        icon = R.drawable.ic_settings,
        route = SETTINGS
    )
}
