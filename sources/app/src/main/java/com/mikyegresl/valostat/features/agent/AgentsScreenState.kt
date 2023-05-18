package com.mikyegresl.valostat.features.agent

import com.mikyegresl.valostat.base.model.agent.AgentDto
import com.mikyegresl.valostat.base.model.agent.AgentOriginDto
import com.mikyegresl.valostat.common.state.BaseState

sealed class AgentsScreenState: BaseState {

    object AgentsScreenLoadingState : AgentsScreenState()

    class AgentsScreenErrorState(val t: Throwable) : AgentsScreenState()

    data class AgentsScreenDataState(
        val agents: List<AgentDto> = emptyList(),
        val agentOrigins: Map<String, AgentOriginDto> = emptyMap(),
        val pointsForUltimate: Map<String, Int> = emptyMap()
    ) : AgentsScreenState()
}