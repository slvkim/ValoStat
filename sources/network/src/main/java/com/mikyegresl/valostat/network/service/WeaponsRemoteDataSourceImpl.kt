package com.mikyegresl.valostat.network.service

import com.mikyegresl.valostat.base.error.ErrorHandler
import com.mikyegresl.valostat.base.network.model.weapon.WeaponsResponse
import com.mikyegresl.valostat.base.network.service.WeaponsRemoteDataSource
import com.mikyegresl.valostat.network.api.ValorantApi

class WeaponsRemoteDataSourceImpl(
    private val api: ValorantApi,
    private val errorHandler: ErrorHandler
) : WeaponsRemoteDataSource {

    override suspend fun getWeapons(): WeaponsResponse =
        errorHandler.handleError {
            api.getWeapons()
        }
}