package com.mikyegresl.valostat.base.network.service

import com.google.gson.JsonElement

interface WeaponsRemoteDataSource {

    suspend fun getWeapons(): JsonElement
}