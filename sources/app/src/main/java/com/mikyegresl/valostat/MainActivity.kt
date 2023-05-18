package com.mikyegresl.valostat

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mikyegresl.valostat.features.agent.AgentsScreen
import com.mikyegresl.valostat.features.agent.AgentsViewModel
import com.mikyegresl.valostat.features.map.MapsScreen
import com.mikyegresl.valostat.features.video.VideosScreen
import com.mikyegresl.valostat.features.video.VideosViewModel
import com.mikyegresl.valostat.features.weapon.WeaponsScreen
import com.mikyegresl.valostat.features.weapon.WeaponsViewModel
import com.mikyegresl.valostat.navigation.GlobalNavItem
import com.mikyegresl.valostat.ui.theme.ValoStatTheme
import com.mikyegresl.valostat.ui.theme.mainBackgroundDark
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mikyegresl.valostat.features.agent.details.AgentDetailsScreen
import com.mikyegresl.valostat.navigation.NavigationItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val agentsViewModel by viewModel<AgentsViewModel>()

    private val weaponsViewModel by viewModel<WeaponsViewModel>()

    private val videosViewModel by viewModel<VideosViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ValoStatTheme {
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = mainBackgroundDark,
                        darkIcons = false
                    )
                }
                MainScreen()
            }
        }
    }

    @Composable
    fun MainScreen() {
        val navController = rememberNavController()

        Scaffold(
            topBar = {  },
            bottomBar = { BottomNavigationBar(navController = navController) }
        ) { offset ->
            Box(modifier = Modifier.padding(offset)) {
                GlobalNavGraph(navController = navController)
            }
        }
    }

    @Composable
    fun BottomNavigationBar(navController: NavController) {
        val items = listOf(
            GlobalNavItem.Agents,
            GlobalNavItem.Weapons,
            GlobalNavItem.Maps,
            GlobalNavItem.Videos
        )
        BottomNavigation(
            backgroundColor = colorResource(id = R.color.black),
            contentColor = colorResource(id = R.color.white)
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            items.forEach { item ->
                BottomNavigationItem(
                    icon = { Icon(painterResource(id = item.icon), contentDescription = stringResource(id = item.title)) },
                    label = { Text(text = stringResource(id = item.title), fontSize = 9.sp) },
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.Gray,
                    alwaysShowLabel = true,
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let { screen_route ->
                                popUpTo(screen_route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }

    @Composable
    fun GlobalNavGraph(navController: NavHostController) {
        NavHost(
            navController = navController,
            startDestination = GlobalNavItem.Agents.route
        ) {
            composable(GlobalNavItem.Agents.route) {
                AgentsScreen(agentsViewModel.state) {
                    navController.navigate("${NavigationItem.AgentDetails.route}/$it") {
                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
            composable(GlobalNavItem.Weapons.route) {
                WeaponsScreen(weaponsViewModel.state)
            }
            composable(GlobalNavItem.Maps.route) {
                MapsScreen()
            }
            composable(GlobalNavItem.Videos.route) {
                VideosScreen(
                    videosScreenState = videosViewModel.state,
                    onEnterFullscreen = {
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    },
                    onExitFullscreen = {
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    }
                )
            }
            composable(
                route = "${NavigationItem.AgentDetails.route}/{${NavigationItem.AgentDetails.agentId}}",
                arguments = listOf(
                    navArgument(NavigationItem.AgentDetails.agentId) {
                        type = NavType.StringType
                    }
                )
            ) {
                it.arguments?.getString(NavigationItem.AgentDetails.agentId)?.let { agentId ->
                    AgentDetailsScreen(
                        agentsViewModel.getAgentDetailsState(agentId)
                    ) {
                        navController.navigateUp()
                    }
                }
            }
        }
    }
}