package com.mikyegresl.valostat.base.model.weapon.stats

data class WeaponDamageRangeDto(
    val bodyDamage: Int,
    val headDamage: Double,
    val legDamage: Double,
    val rangeEndMeters: Int,
    val rangeStartMeters: Int
)