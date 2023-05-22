package com.mikyegresl.valostat.base.storage.service

import com.google.gson.JsonElement
import com.mikyegresl.valostat.base.model.agent.AgentOriginDto

interface AgentsLocalDataSource {

    fun getAgentOrigin(id: String): AgentOriginDto

    fun getPointsForUltimate(id: String): Int

    suspend fun getAgents(): JsonElement?

    suspend fun saveAgents(agents: JsonElement)

    suspend fun removeAgents()
}