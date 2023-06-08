package com.mikyegresl.valostat.features.settings

import androidx.lifecycle.viewModelScope
import com.mikyegresl.valostat.base.model.ValoStatLocale
import com.mikyegresl.valostat.base.repository.SettingsRepository
import com.mikyegresl.valostat.common.viewmodel.BaseNavigationViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val repository: SettingsRepository
) : BaseNavigationViewModel<SettingsScreenState>() {

    companion object {
        private const val TAG = "SettingsViewModel"
    }

    override val _state = MutableStateFlow<SettingsScreenState>(
        SettingsScreenState.SettingsLoadingState
    )

    init {
        getCurrentLocale()
    }

    private fun getCurrentLocale() =
        viewModelScope.launch {
            val locale = repository.getCurrentLocale()

            _state.value = SettingsScreenState.SettingsDataState(locale)
        }

    fun saveCurrentLocale(locale: ValoStatLocale, processLocaleSaved: () -> Unit) =
        viewModelScope.launch {
            repository.saveCurrentLocale(locale)
        }.invokeOnCompletion {
            processLocaleSaved()
        }
}