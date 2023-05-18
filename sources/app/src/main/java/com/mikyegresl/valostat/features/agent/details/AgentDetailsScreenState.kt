package com.mikyegresl.valostat.features.agent.details

import com.mikyegresl.valostat.base.model.agent.AgentDto
import com.mikyegresl.valostat.base.model.agent.AgentOriginDto

data class AgentDetailsScreenState(
    val details: AgentDto,
    val origin: AgentOriginDto,
    val pointsForUltimate: Int
)
