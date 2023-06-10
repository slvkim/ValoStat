package com.mikyegresl.valostat.base.storage.service

import com.google.gson.JsonElement
import com.mikyegresl.valostat.base.model.ValoStatLocale
import com.mikyegresl.valostat.base.model.agent.AgentOriginDto

interface AgentsLocalDataSource {

    fun getAgentOrigin(id: String): AgentOriginDto

    fun getPointsForUltimate(id: String): Int

    suspend fun getAgents(locale: ValoStatLocale): JsonElement?

    suspend fun saveAgents(agents: JsonElement, locale: ValoStatLocale)

    suspend fun removeAgents(locale: ValoStatLocale)
}