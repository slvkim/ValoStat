package com.mikyegresl.valostat.features.agent

import com.mikyegresl.valostat.base.model.agent.AgentDto
import com.mikyegresl.valostat.base.repository.AgentsRepository
import com.mikyegresl.valostat.common.viewmodel.BaseNavigationViewModel
import com.mikyegresl.valostat.features.agent.details.AgentDetailsScreenState
import kotlinx.coroutines.flow.MutableStateFlow

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
            onSuccessRemote = ::processSuccessfulLoad,
            onSuccessLocal = ::processSuccessfulLoad,
            onError = {
                _state.value = AgentsScreenState.AgentsScreenErrorState(it)
                true
            }
        )

    private fun processSuccessfulLoad(agents: List<AgentDto>) {
        val ids = agents.map { it.uuid }
        _state.value = AgentsScreenState.AgentsScreenDataState(
            agents,
            repository.getAgentsOrigin(ids),
            repository.getPointsForUltimate(ids)
        )
    }

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