package com.mikyegresl.valostat.base.repository

import com.mikyegresl.valostat.base.model.weapon.WeaponDto
import kotlinx.coroutines.flow.Flow

interface WeaponsRepository {

    fun getWeapons(): Flow<List<WeaponDto>>
}