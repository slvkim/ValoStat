package com.mikyegresl.valostat.base.network.service

import com.mikyegresl.valostat.base.network.model.weapon.WeaponsResponse

interface WeaponsRemoteDataSource {

    suspend fun getWeapons(): WeaponsResponse
}