package com.mikyegresl.valostat.features.agent

import androidx.lifecycle.viewModelScope
import com.mikyegresl.valostat.base.model.agent.AgentDto
import com.mikyegresl.valostat.base.repository.AgentsRepository
import com.mikyegresl.valostat.base.repository.SettingsRepository
import com.mikyegresl.valostat.common.viewmodel.BaseNavigationViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AgentsViewModel(
    private val repository: AgentsRepository,
    private val settingsRepository: SettingsRepository
) : BaseNavigationViewModel<AgentsScreenState>() {

    companion object {
        private const val TAG = "AgentsViewModel"
    }

    override val _state = MutableStateFlow<AgentsScreenState>(
        AgentsScreenState.AgentsScreenLoadingState
    )

    init {
        loadAgents()
    }

    private fun loadAgents() =
        viewModelScope.launch {
            val locale = settingsRepository.getCurrentLocale()

            doBackground(
                repository.getAgents(locale),
                onLoading = {
                    _state.value = AgentsScreenState.AgentsScreenLoadingState
                },
                onSuccessRemote = ::processSuccessfulLoad,
                onSuccessLocal = ::processSuccessfulLoad,
                onError = {
                    _state.value = AgentsScreenState.AgentsScreenErrorState(it)
                    true
                }
            )
        }

    private fun processSuccessfulLoad(agents: List<AgentDto>) {
        val ids = agents.map { it.uuid }
        _state.value = AgentsScreenState.AgentsScreenDataState(
            agents,
            repository.getAgentsOrigin(ids),
            repository.getPointsForUltimate(ids)
        )
    }
}