package com.mikyegresl.valostat.features.weapon

import com.mikyegresl.valostat.base.model.weapon.WeaponDto
import com.mikyegresl.valostat.base.repository.WeaponsRepository
import com.mikyegresl.valostat.common.viewmodel.BaseNavigationViewModel
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

    private fun processSuccessfulLoad(weapons: List<WeaponDto>) {
        _state.value = WeaponsScreenState.WeaponsScreenDataState(weapons)
    }

    private fun loadWeapons() =
        doBackground(
            repository.getWeapons(),
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

    fun getWeaponDetails(weaponId: String): WeaponDto? =
        (currentState as? WeaponsScreenState.WeaponsScreenDataState)?.let { dataState ->
            return@let dataState.weapons.find<WeaponDto> { it.uuid == weaponId }
        }
}