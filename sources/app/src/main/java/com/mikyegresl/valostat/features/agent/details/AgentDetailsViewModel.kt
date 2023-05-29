package com.mikyegresl.valostat.features.agent.details

import com.mikyegresl.valostat.base.repository.AgentsRepository
import com.mikyegresl.valostat.common.viewmodel.BaseNavigationViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class AgentDetailsViewModel(
    private val repository: AgentsRepository,
    agentId: String
) : BaseNavigationViewModel<AgentDetailsScreenState>() {

    companion object {
        private const val TAG = "AgentDetailsViewModel"
    }

    override val _state = MutableStateFlow<AgentDetailsScreenState>(
        AgentDetailsScreenState.AgentDetailsLoadingState
    )

    init {
        loadAgentDetails(agentId)
    }

    private fun loadAgentDetails(agentId: String) =
        doBackground(
            repository.getAgentDetails(agentId),
            onLoading = {
                _state.value = AgentDetailsScreenState.AgentDetailsLoadingState
            },
            onSuccessLocal = {
                _state.value = AgentDetailsScreenState.AgentDetailsDataState(
                    details = it,
                    origin = repository.getAgentsOrigin(it.uuid),
                    pointsForUltimate = repository.getPointsForUltimate(it.uuid)
                )
            },
            onError = {
                _state.value = AgentDetailsScreenState.AgentDetailsErrorState(it)
                true
            }
        )

    fun dispatchIntent(intent: AgentDetailsIntent) {
        when (intent) {
            is AgentDetailsIntent.RefreshAgentDetailsIntent -> {

            }
            is AgentDetailsIntent.AudioClickedIntent -> {
                (currentState as? AgentDetailsScreenState.AgentDetailsDataState)?.let {
                    updateState(it.copy(activeVoiceline = intent.voiceline))
                }
            }
            is AgentDetailsIntent.AudioDisposeIntent -> {
                val dataState = (currentState as? AgentDetailsScreenState.AgentDetailsDataState) ?: return
                if (dataState.activeVoiceline == intent.voiceline) {
                    updateState(dataState.copy(activeVoiceline = null))
                }
            }
        }
    }
}