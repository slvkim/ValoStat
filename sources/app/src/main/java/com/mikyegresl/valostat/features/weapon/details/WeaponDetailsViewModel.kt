package com.mikyegresl.valostat.features.weapon.details

import androidx.lifecycle.viewModelScope
import com.mikyegresl.valostat.base.model.ValoStatLocale
import com.mikyegresl.valostat.base.repository.WeaponsRepository
import com.mikyegresl.valostat.common.viewmodel.BaseNavigationViewModel
import com.mikyegresl.valostat.features.video.player.exoplayer.ExoPlayerConfig
import com.mikyegresl.valostat.features.weapon.details.WeaponDetailsIntent.ContinueVideoPlaybackIntent
import com.mikyegresl.valostat.features.weapon.details.WeaponDetailsIntent.UpdateWeaponDetailsIntent
import com.mikyegresl.valostat.features.weapon.details.WeaponDetailsScreenState.WeaponDetailsErrorState
import com.mikyegresl.valostat.features.weapon.details.WeaponDetailsScreenState.WeaponDetailsInGeneralState
import com.mikyegresl.valostat.features.weapon.details.WeaponDetailsScreenState.WeaponDetailsInGeneralState.WeaponDetailsInDataState
import com.mikyegresl.valostat.features.weapon.details.WeaponDetailsScreenState.WeaponDetailsInGeneralState.WeaponDetailsInFullscreenState
import com.mikyegresl.valostat.features.weapon.details.WeaponDetailsScreenState.WeaponDetailsLoadingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class WeaponDetailsViewModel(
    private val repository: WeaponsRepository,
) : BaseNavigationViewModel<WeaponDetailsScreenState>() {

    companion object {
        private const val TAG = "WeaponsViewModel"
    }

    override val _state = MutableStateFlow<WeaponDetailsScreenState>(
        WeaponDetailsLoadingState
    )

    private fun loadWeaponDetails(weaponId: String, locale: ValoStatLocale) {
        viewModelScope.launch {
            doBackground(
                repository.getWeaponDetails(weaponId, locale),
                onLoading = {
                    _state.value = WeaponDetailsLoadingState
                },
                onSuccessLocal = {
                    _state.value = WeaponDetailsInDataState(
                        details = it,
                        activeVideoChroma = null,
                        playerConfig = ExoPlayerConfig.DEFAULT_PLAYER_CONFIG,
                        continuePlayback = false
                    )
                },
                onError = {
                    _state.value = WeaponDetailsErrorState(it)
                    true
                }
            )
        }
    }

    fun dispatchIntent(intent: WeaponDetailsIntent) {
        when (intent) {
            is WeaponDetailsIntent.VideoClickedIntent -> {
                (currentState as? WeaponDetailsInDataState)?.let {
                    updateState(
                        it.copy(
                            activeVideoChroma = intent.chroma,
                            continuePlayback = false,
                            playerConfig = ExoPlayerConfig.DEFAULT_PLAYER_CONFIG
                        )
                    )
                }
            }
            is WeaponDetailsIntent.VideoDisposeIntent -> {
                val dataState = (currentState as? WeaponDetailsInDataState) ?: return
                if (dataState.activeVideoChroma == intent.chroma) {
                    updateState(dataState.copy(activeVideoChroma = null))
                }
            }
            is UpdateWeaponDetailsIntent -> {
                loadWeaponDetails(intent.weaponId, intent.locale)
            }
            is ContinueVideoPlaybackIntent -> {
                val dataState = (currentState as? WeaponDetailsInGeneralState) ?: return

                val newState = if (intent.playerConfig.areInFullscreenFromStart) {
                    WeaponDetailsInFullscreenState(
                        details = dataState.details,
                        activeVideoChroma = dataState.activeVideoChroma!!,
                        playerConfig = intent.playerConfig,
                        continuePlayback = true
                    )
                } else {
                    WeaponDetailsInDataState(
                        details = dataState.details,
                        activeVideoChroma = dataState.activeVideoChroma,
                        playerConfig = intent.playerConfig,
                        continuePlayback = true
                    )
                }
                updateState(newState)
            }
            else -> {}
        }
    }
}