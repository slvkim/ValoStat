package com.example.valostat.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.valostat.R

sealed class GlobalNavItem(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: String
) {
    companion object {
        private const val AGENTS = "agents"
        private const val WEAPONS = "weapons"
        private const val MAPS = "maps"
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

    object Maps : GlobalNavItem (
        title = R.string.maps,
        icon = R.drawable.ic_maps,
        route = MAPS
    )
}
