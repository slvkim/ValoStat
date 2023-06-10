package com.mikyegresl.valostat.features.weapon

import androidx.lifecycle.viewModelScope
import com.mikyegresl.valostat.base.model.ValoStatLocale
import com.mikyegresl.valostat.base.model.weapon.WeaponDto
import com.mikyegresl.valostat.base.repository.WeaponsRepository
import com.mikyegresl.valostat.common.viewmodel.BaseNavigationViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class WeaponsViewModel(
    private val repository: WeaponsRepository
) : BaseNavigationViewModel<WeaponsScreenState>() {

    companion object {
        private const val TAG = "WeaponsViewModel"
    }

    override val _state = MutableStateFlow<WeaponsScreenState>(
        WeaponsScreenState.WeaponsScreenLoadingState
    )

    fun dispatchIntent(intent: WeaponsIntent) = when (intent) {
        is WeaponsIntent.UpdateWeaponsIntent -> {
            loadWeapons(intent.locale)
        }
    }

    private fun processSuccessfulLoad(weapons: List<WeaponDto>) {
        _state.value = WeaponsScreenState.WeaponsScreenDataState(weapons)
    }

    private fun loadWeapons(locale: ValoStatLocale) {
        viewModelScope.launch {
            doBackground(
                repository.getWeapons(locale),
                onLoading = {
                    _state.value = WeaponsScreenState.WeaponsScreenLoadingState
                },
                onSuccessRemote = ::processSuccessfulLoad,
                onSuccessLocal = ::processSuccessfulLoad,
                onError = {
                    _state.value = WeaponsScreenState.WeaponsScreenErrorState(it)
                    true
                }
            )
        }
    }
}