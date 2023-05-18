package com.mikyegresl.valostat.base.database.service

import com.mikyegresl.valostat.base.model.agent.AgentOriginDto

interface AgentsLocalDataSource {

    fun getAgentOrigin(id: String): AgentOriginDto

    fun getPointsForUltimate(id: String): Int
}