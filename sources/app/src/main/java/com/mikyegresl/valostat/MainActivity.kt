package com.mikyegresl.valostat

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mikyegresl.valostat.base.common.converter.NavigationEncoderFactory
import com.mikyegresl.valostat.base.launcher.ExternalLinkLauncher
import com.mikyegresl.valostat.base.model.ValoStatLocale
import com.mikyegresl.valostat.features.agent.AgentsIntent
import com.mikyegresl.valostat.features.agent.AgentsViewModel
import com.mikyegresl.valostat.features.agent.agentsRouter
import com.mikyegresl.valostat.features.agent.details.agentDetailsRouter
import com.mikyegresl.valostat.features.news.NewsIntent
import com.mikyegresl.valostat.features.news.NewsViewModel
import com.mikyegresl.valostat.features.news.details.newsDetailsRouter
import com.mikyegresl.valostat.features.news.newsRouter
import com.mikyegresl.valostat.features.settings.SettingsScreenActions
import com.mikyegresl.valostat.features.settings.settingsRouter
import com.mikyegresl.valostat.features.video.player.ActivityConfigHandler
import com.mikyegresl.valostat.features.video.player.exoplayer.ExoPlayerConfig
import com.mikyegresl.valostat.features.weapon.WeaponsIntent
import com.mikyegresl.valostat.features.weapon.WeaponsViewModel
import com.mikyegresl.valostat.features.weapon.details.WeaponDetailsIntent
import com.mikyegresl.valostat.features.weapon.details.WeaponDetailsScreenActions
import com.mikyegresl.valostat.features.weapon.details.WeaponDetailsViewModel
import com.mikyegresl.valostat.features.weapon.details.weaponDetailsRouter
import com.mikyegresl.valostat.features.weapon.weaponsRouter
import com.mikyegresl.valostat.navigation.GlobalNavItem
import com.mikyegresl.valostat.provider.AppLocaleProvider
import com.mikyegresl.valostat.ui.dimen.ElemSize
import com.mikyegresl.valostat.ui.theme.ValoStatTheme
import com.mikyegresl.valostat.ui.theme.mainBackgroundDark
import com.mikyegresl.valostat.utils.openWebsite
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val localeConfigProvider by lazy {
        AppLocaleProvider(resources)
    }

    private val linkLauncher: ExternalLinkLauncher by inject()

    private val newsViewModel by viewModel<NewsViewModel>()

    private val agentsViewModel by viewModel<AgentsViewModel>()

    private val weaponsViewModel by viewModel<WeaponsViewModel>()

    private val weaponDetailsViewModel by viewModel<WeaponDetailsViewModel>()

    private val encoderFactory: NavigationEncoderFactory by inject()

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
            bottomBar = {
                val currentOrientation = LocalConfiguration.current.orientation
                val hideBottomBar = currentOrientation == ORIENTATION_LANDSCAPE

                if (!hideBottomBar) {
                    BottomNavigationBar(navController = navController)
                }
            }
        ) { offset ->
            Box(modifier = Modifier.padding(offset)) {
                GlobalNavGraph(navController = navController)
            }
        }
    }

    @Composable
    fun BottomNavigationBar(navController: NavController) {
        BottomNavigation(
            backgroundColor = colorResource(id = R.color.black),
            contentColor = colorResource(id = R.color.white)
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            GlobalNavItem.getItemList().forEach { item ->
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
                        if (item.route == currentRoute) {
                            return@BottomNavigationItem
                        }
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let { startRoute ->
                                popUpTo(startRoute)
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
            newsRouter(
                state = newsViewModel.state,
                navController = navController,
                builder = this,
                encoder = encoderFactory.getNewsNavigationEncoder(),
                openWebPage = {
                    this@MainActivity.openWebsite(it)
                }
            )
            newsDetailsRouter(
                currentLocale = localeConfigProvider.appLocale,
                navController = navController,
                builder = this,
                encoderFactory.getNewsNavigationEncoder()
            )
            agentsRouter(
                state = agentsViewModel.state,
                navController = navController,
                builder = this
            )
            agentDetailsRouter(
                currentLocale = localeConfigProvider.appLocale,
                navController = navController,
                builder = this
            )
            weaponsRouter(
                state = weaponsViewModel.state,
                navController = navController,
                builder = this
            )
            weaponDetailsRouter(
                currentLocale = localeConfigProvider.appLocale,
                state = weaponDetailsViewModel.state,
                actions = WeaponDetailsScreenActions(
                    onVideoItemClicked = {
                        weaponDetailsViewModel.dispatchIntent(WeaponDetailsIntent.VideoClickedIntent(it))
                    },
                    onEnterFullscreen = {
                        enterFullscreenMode()
                    },
                    onExitFullscreen = {
                        exitFullscreenMode()
                    },
                    beforeEnterFullscreen = { playbackPosition, playOnInit ->
                        weaponDetailsViewModel.dispatchIntent(
                            WeaponDetailsIntent.ContinueVideoPlaybackIntent(
                                ExoPlayerConfig.getEnterFullscreenConfig(
                                    playbackPosition, playOnInit
                                )
                            )
                        )
                    },
                    beforeExitFullscreen = { playbackPosition, playOnInit ->
                        weaponDetailsViewModel.dispatchIntent(
                            WeaponDetailsIntent.ContinueVideoPlaybackIntent(
                                ExoPlayerConfig.getExitFullscreenConfig(
                                    playbackPosition, playOnInit
                                )
                            )
                        )
                    },
                    onBackPressed = {
                        navController.navigateUp()
                    },
                    onSkinLeftFocus = {
                        weaponDetailsViewModel.dispatchIntent(WeaponDetailsIntent.VideoDisposeIntent(it))
                    },
                    onUpdateWeaponDetails = { weaponId, locale ->
                        weaponDetailsViewModel.dispatchIntent(WeaponDetailsIntent.UpdateWeaponDetailsIntent(weaponId, locale))
                    }
                ),
                builder = this
            )
            settingsRouter(
                locales = localeConfigProvider.locales,
                actions = SettingsScreenActions(
                    onAppLangSwitched = ::onAppLanguageChanged,
                    onOfficialPageClick = { linkLauncher.openOfficialPage(this@MainActivity) },
                    onRateAppClick = { linkLauncher.openRateAppPage(this@MainActivity) }
                ),
                builder = this
            )
        }
    }

    private fun onAppLanguageChanged(locale: ValoStatLocale) {
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(locale.title)
        )
    }

    override fun onStart() {
        super.onStart()

        newsViewModel.dispatchIntent(
            NewsIntent.UpdateNewsIntent(
                localeConfigProvider.appLocale
            )
        )

        agentsViewModel.dispatchIntent(
            AgentsIntent.UpdateAgentsIntent(
                localeConfigProvider.appLocale
            )
        )

        weaponsViewModel.dispatchIntent(
            WeaponsIntent.UpdateWeaponsIntent(
                localeConfigProvider.appLocale
            )
        )
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        newsViewModel.dispatchIntent(
            NewsIntent.UpdateNewsIntent(
                localeConfigProvider.appLocale
            )
        )

        agentsViewModel.dispatchIntent(
            AgentsIntent.UpdateAgentsIntent(
                localeConfigProvider.appLocale
            )
        )

        weaponsViewModel.dispatchIntent(
            WeaponsIntent.UpdateWeaponsIntent(
                localeConfigProvider.appLocale
            )
        )
    }

    private fun enterFullscreenMode() {
        ActivityConfigHandler.hideSystemUI(this@MainActivity)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    private fun exitFullscreenMode() {
        ActivityConfigHandler.showSystemUI(this@MainActivity)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}