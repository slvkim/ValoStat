package com.mikyegresl.valostat.network.service

import com.google.gson.JsonElement
import com.mikyegresl.valostat.base.error.ErrorHandler
import com.mikyegresl.valostat.base.network.service.AgentsRemoteDataSource
import com.mikyegresl.valostat.network.api.ValorantApi

class AgentsRemoteDataSourceImpl(
    private val api: ValorantApi,
    private val errorHandler: ErrorHandler
) : AgentsRemoteDataSource {

    override suspend fun getAgents(): JsonElement =
        errorHandler.handleError {
            api.getAgents()
        }
}