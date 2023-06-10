package com.mikyegresl.valostat

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.core.os.LocaleListCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mikyegresl.valostat.base.model.ValoStatLocale
import com.mikyegresl.valostat.features.agent.AgentsIntent
import com.mikyegresl.valostat.features.agent.AgentsScreen
import com.mikyegresl.valostat.features.agent.AgentsViewModel
import com.mikyegresl.valostat.features.agent.details.AgentDetailsScreen
import com.mikyegresl.valostat.features.settings.SettingsScreen
import com.mikyegresl.valostat.features.video.VideosViewModel
import com.mikyegresl.valostat.features.weapon.WeaponsIntent
import com.mikyegresl.valostat.features.weapon.WeaponsScreen
import com.mikyegresl.valostat.features.weapon.WeaponsViewModel
import com.mikyegresl.valostat.features.weapon.details.WeaponDetailsScreen
import com.mikyegresl.valostat.navigation.GlobalNavItem
import com.mikyegresl.valostat.navigation.NavigationItem
import com.mikyegresl.valostat.providers.AppLocaleProvider
import com.mikyegresl.valostat.ui.dimen.ElemSize
import com.mikyegresl.valostat.ui.theme.ValoStatTheme
import com.mikyegresl.valostat.ui.theme.mainBackgroundDark
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivityClass"
    }

    private val localeConfigProvider by lazy {
        AppLocaleProvider(
            mapOf(
                R.string.en to ValoStatLocale.EN,
                R.string.ru to ValoStatLocale.RU,
                R.string.kr to ValoStatLocale.KR
            ).mapKeys { getString(it.key) }
        )
    }

    private val agentsViewModel by viewModel<AgentsViewModel> {
        parametersOf(
            localeConfigProvider.appLocale
        )
    }

    private val weaponsViewModel by viewModel<WeaponsViewModel> {
        parametersOf(
            localeConfigProvider.appLocale
        )
    }

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
//            GlobalNavItem.Maps,
//            GlobalNavItem.Videos,
            GlobalNavItem.Settings
        )
        BottomNavigation(
            backgroundColor = colorResource(id = R.color.black),
            contentColor = colorResource(id = R.color.white)
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            items.forEach { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            modifier = Modifier.size(ElemSize.Dp16),
                            painter = painterResource(id = item.icon),
                            contentDescription = stringResource(id = item.title)
                    ) },
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
                        navController.graph.startDestinationRoute?.let { screenRoute ->
                            popUpTo(screenRoute) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
            composable(GlobalNavItem.Weapons.route) {
                WeaponsScreen(weaponsViewModel.state) {
                    navController.navigate("${NavigationItem.WeaponDetails.route}/$it") {
                        navController.graph.startDestinationRoute?.let { screenRoute ->
                            popUpTo(GlobalNavItem.Weapons.route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
//            composable(GlobalNavItem.Maps.route) {
//                MapsScreen()
//            }
//            composable(GlobalNavItem.Videos.route) {
//                VideosScreen(
//                    videosScreenState = videosViewModel.state,
//                    onEnterFullscreen = {
//                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
//                    },
//                    onExitFullscreen = {
//                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
//                    }
//                )
//            }
            composable(GlobalNavItem.Settings.route) {
                SettingsScreen(
                    locales = localeConfigProvider.locales,
                    onAppLangSwitched = {
                        onAppLanguageChanged(it)
                    }
                )
            }
            composable(
                route = "${NavigationItem.WeaponDetails.route}/{${NavigationItem.WeaponDetails.weaponId}}",
                arguments = listOf(
                    navArgument(NavigationItem.WeaponDetails.weaponId) {
                        type = NavType.StringType
                    }
                )
            ) {
                it.arguments?.getString(NavigationItem.WeaponDetails.weaponId)?.let { weaponId ->
                    WeaponDetailsScreen(
                        weaponId = weaponId,
                        locale = localeConfigProvider.appLocale
                    ) {
                        navController.navigateUp()
                    }
                }
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
                        agentId = agentId,
                        locale = localeConfigProvider.appLocale
                    ) {
                        navController.navigateUp()
                    }
                }
            }
        }
    }

    private fun onAppLanguageChanged(locale: ValoStatLocale) {
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(locale.title)
        )
    }

    override fun onStart() {
        super.onStart()
        weaponsViewModel.dispatchIntent(
            WeaponsIntent.UpdateWeaponsIntent(
                localeConfigProvider.appLocale
            )
        )
        agentsViewModel.dispatchIntent(
            AgentsIntent.UpdateAgentsIntent(
                localeConfigProvider.appLocale
            )
        )
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        weaponsViewModel.dispatchIntent(
            WeaponsIntent.UpdateWeaponsIntent(
                localeConfigProvider.appLocale
            )
        )
        agentsViewModel.dispatchIntent(
            AgentsIntent.UpdateAgentsIntent(
                localeConfigProvider.appLocale
            )
        )
    }
}