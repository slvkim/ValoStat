package com.mikyegresl.valostat.features.agent.details

import com.mikyegresl.valostat.base.model.ValoStatLocale
import com.mikyegresl.valostat.base.repository.AgentsRepository
import com.mikyegresl.valostat.common.viewmodel.BaseNavigationViewModel
import kotlinx.coroutines.flow.MutableStateFlow

private const val TAG = "AgentDetailsViewModel"

class AgentDetailsViewModel(
    private val repository: AgentsRepository
) : BaseNavigationViewModel<AgentDetailsScreenState>() {

    override val _state = MutableStateFlow<AgentDetailsScreenState>(
        AgentDetailsScreenState.AgentDetailsLoadingState
    )

    private fun loadAgentDetails(agentId: String, locale: ValoStatLocale) = doBackground(
        repository.getAgentDetails(agentId, locale),
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
            is AgentDetailsIntent.UpdateAgentDetailsIntent -> {
                loadAgentDetails(intent.agentId, intent.locale)
            }
            is AgentDetailsIntent.AudioClickedIntent -> {
                (currentState as? AgentDetailsScreenState.AgentDetailsDataState)?.let {
                    updateState(it.copy(activeVoiceline = intent.voiceline))
                }
            }
        }
    }
}