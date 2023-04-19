package com.example.valostat

import android.os.Bundle
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.valostat.features.agent.AgentsScreen
import com.example.valostat.features.map.MapsScreen
import com.example.valostat.features.weapon.WeaponsScreen
import com.example.valostat.navigation.GlobalNavItem
import com.example.valostat.ui.theme.ValoStatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ValoStatTheme {
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
            GlobalNavItem.Maps
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
                AgentsScreen()
            }
            composable(GlobalNavItem.Weapons.route) {
                WeaponsScreen()
            }
            composable(GlobalNavItem.Maps.route) {
                MapsScreen()
            }
        }
    }
}