package com.mikyegresl.valostat.features.weapon.details

import com.mikyegresl.valostat.base.repository.WeaponsRepository
import com.mikyegresl.valostat.common.viewmodel.BaseNavigationViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class WeaponDetailsViewModel(
    private val repository: WeaponsRepository,
    weaponId: String
) : BaseNavigationViewModel<WeaponDetailsScreenState>() {

    companion object {
        private const val TAG = "WeaponsViewModel"
    }

    override val _state = MutableStateFlow<WeaponDetailsScreenState>(
        WeaponDetailsScreenState.WeaponDetailsLoadingState
    )

    init {
        loadWeaponDetails(weaponId)
    }

    private fun loadWeaponDetails(weaponId: String) =
        doBackground(
            repository.getWeaponDetails(weaponId),
            onLoading = {
                _state.value = WeaponDetailsScreenState.WeaponDetailsLoadingState
            },
            onSuccessLocal = {
                _state.value = WeaponDetailsScreenState.WeaponDetailsDataState(it)
            },
            onError = {
                _state.value = WeaponDetailsScreenState.WeaponDetailsErrorState(it)
                true
            }
        )
    fun dispatchIntent(intent: WeaponDetailsIntent) {
        when (intent) {
            is WeaponDetailsIntent.VideoClickedIntent -> {
                (currentState as? WeaponDetailsScreenState.WeaponDetailsDataState)?.let {
                    updateState(it.copy(activeVideoChroma = intent.chroma))
                }
            }
            is WeaponDetailsIntent.VideoDisposeIntent -> {
                val dataState = (currentState as? WeaponDetailsScreenState.WeaponDetailsDataState) ?: return
                if (dataState.activeVideoChroma == intent.chroma) {
                    updateState(dataState.copy(activeVideoChroma = null))
                }
            }
            is WeaponDetailsIntent.RefreshWeaponDetailsIntent -> {
                loadWeaponDetails(intent.weaponId)
            }
        }
    }
}