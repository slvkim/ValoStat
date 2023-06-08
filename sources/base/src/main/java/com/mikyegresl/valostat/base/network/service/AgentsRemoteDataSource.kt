package com.mikyegresl.valostat.base.network.service

import com.google.gson.JsonElement
import com.mikyegresl.valostat.base.model.ValoStatLocale

interface AgentsRemoteDataSource {

    suspend fun getAgents(lang: ValoStatLocale = ValoStatLocale.EN): JsonElement
}