package com.mikyegresl.valostat.features.agent

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.mikyegresl.valostat.common.viewmodel.BaseNavigationViewModel
import com.mikyegresl.valostat.base.repository.AgentsRepository
import com.mikyegresl.valostat.features.agent.details.AgentDetailsAsDataState
import com.mikyegresl.valostat.features.agent.details.AgentDetailsScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AgentsViewModel(
    private val repository: AgentsRepository
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
        doBackground(
            repository.getAgents(),
            onLoading = {
                _state.value = AgentsScreenState.AgentsScreenLoadingState
            },
            onSuccessRemote = { agents ->
                val ids = agents.map { it.uuid }
                _state.value = AgentsScreenState.AgentsScreenDataState(
                    agents,
                    repository.getAgentsOrigin(ids),
                    repository.getPointsForUltimate(ids)
                )
            },
            onError = {
                _state.value = AgentsScreenState.AgentsScreenErrorState(it)
                true
            }
        )

    fun getAgentDetailsState(id: String): AgentDetailsScreenState? =
        (currentState as? AgentsScreenState.AgentsScreenDataState)?.let { dataState ->
            val details = dataState.agents.find { it.uuid == id }
            val origin = dataState.agentOrigins[id]
            val pts = dataState.pointsForUltimate[id]

            if (details == null || origin == null || pts == null) {
                return null
            }
            AgentDetailsScreenState(
                details = details,
                origin = origin,
                pointsForUltimate = pts
            )
        }
}