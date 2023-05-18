package com.mikyegresl.valostat.base.repository

import com.mikyegresl.valostat.base.model.weapon.WeaponDto
import com.mikyegresl.valostat.base.network.Response
import kotlinx.coroutines.flow.Flow

interface WeaponsRepository {
    fun getWeapons(): Flow<Response<List<WeaponDto>>>
}