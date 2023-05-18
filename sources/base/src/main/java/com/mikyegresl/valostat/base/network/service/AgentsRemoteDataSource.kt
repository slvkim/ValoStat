package com.mikyegresl.valostat.base.network.service

import com.mikyegresl.valostat.base.network.model.agent.AgentsResponse

interface AgentsRemoteDataSource {

    suspend fun getAgents(): AgentsResponse
}