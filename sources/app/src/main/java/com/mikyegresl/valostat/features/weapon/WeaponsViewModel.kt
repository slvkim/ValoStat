package com.mikyegresl.valostat.features.weapon

import com.mikyegresl.valostat.common.viewmodel.BaseNavigationViewModel
import com.mikyegresl.valostat.base.repository.WeaponsRepository
import kotlinx.coroutines.flow.MutableStateFlow

class WeaponsViewModel(
    private val repository: WeaponsRepository
) : BaseNavigationViewModel<WeaponsScreenState>() {

    companion object {
        private const val TAG = "WeaponsViewModel"
    }

    override val _state = MutableStateFlow<WeaponsScreenState>(
        WeaponsScreenState.WeaponsScreenLoadingState
    )

    init {
        loadWeapons()
    }

    private fun loadWeapons() =
        doBackground(
            repository.getWeapons(),
            onLoading = {
                _state.value = WeaponsScreenState.WeaponsScreenLoadingState
            },
            onSuccessRemote = { weapons ->
                _state.value = WeaponsScreenState.WeaponsScreenDataState(
                    weapons = weapons
                )
            },
            onError = {
                _state.value = WeaponsScreenState.WeaponsScreenErrorState(it)
                true
            }
        )
}