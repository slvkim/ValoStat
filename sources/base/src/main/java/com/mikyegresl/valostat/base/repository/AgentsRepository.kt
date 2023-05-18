package com.mikyegresl.valostat.base.repository

import com.mikyegresl.valostat.base.model.agent.AgentDto
import com.mikyegresl.valostat.base.model.agent.AgentOriginDto
import com.mikyegresl.valostat.base.network.Response
import kotlinx.coroutines.flow.Flow

interface AgentsRepository {

    fun getAgents(): Flow<Response<List<AgentDto>>>

    fun getAgentsOrigin(ids: List<String>): Map<String, AgentOriginDto>

    fun getPointsForUltimate(ids: List<String>): Map<String, Int>
}