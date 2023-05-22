package com.mikyegresl.valostat.network.service

import com.google.gson.JsonElement
import com.mikyegresl.valostat.base.error.ErrorHandler
import com.mikyegresl.valostat.base.network.service.WeaponsRemoteDataSource
import com.mikyegresl.valostat.network.api.ValorantApi

class WeaponsRemoteDataSourceImpl(
    private val api: ValorantApi,
    private val errorHandler: ErrorHandler
) : WeaponsRemoteDataSource {

    override suspend fun getWeapons(): JsonElement =
        errorHandler.handleError {
            api.getWeapons()
        }
}