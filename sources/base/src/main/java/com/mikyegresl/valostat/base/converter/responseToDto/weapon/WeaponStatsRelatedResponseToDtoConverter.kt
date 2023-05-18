package com.mikyegresl.valostat.base.converter.responseToDto.weapon

import com.mikyegresl.valostat.base.common.converter.Converter
import com.mikyegresl.valostat.base.common.converter.Converter.Companion.EMPTY_STRING
import com.mikyegresl.valostat.base.model.weapon.stats.WeaponAdsStatsDto
import com.mikyegresl.valostat.base.model.weapon.stats.WeaponAirBurstStatsDto
import com.mikyegresl.valostat.base.model.weapon.stats.WeaponAltShotgunStatsDto
import com.mikyegresl.valostat.base.model.weapon.stats.WeaponDamageRangeDto
import com.mikyegresl.valostat.base.model.weapon.stats.WeaponStatsDto
import com.mikyegresl.valostat.base.network.model.weapon.stats.WeaponAdsStatsResponse
import com.mikyegresl.valostat.base.network.model.weapon.stats.WeaponAirBurstStatsResponse
import com.mikyegresl.valostat.base.network.model.weapon.stats.WeaponAltShotgunStatsResponse
import com.mikyegresl.valostat.base.network.model.weapon.stats.WeaponDamageRangeResponse
import com.mikyegresl.valostat.base.network.model.weapon.stats.WeaponStatsResponse

object WeaponStatsRelatedResponseToDtoConverter : Converter<WeaponStatsResponse?, WeaponStatsDto> {

    override fun convert(from: WeaponStatsResponse?): WeaponStatsDto =
        with (from) {
            WeaponStatsDto(
                equipTimeSeconds = this?.equipTimeSeconds ?: 0.0,
                fireRate = this?.fireRate ?: 0.0,
                firstBulletAccuracy = this?.firstBulletAccuracy ?: 0.0,
                magazineSize = this?.magazineSize ?: 0,
                reloadTimeSeconds = this?.reloadTimeSeconds ?: 0.0,
                runSpeedMultiplier = this?.runSpeedMultiplier ?: 0.0,
                shotgunPelletCount = this?.shotgunPelletCount ?: 0,
                adsStats = AdsStatsResponseToDtoConverter.convert(this?.adsStats),
                airBurstStats = AirBurstStatsResponseToDtoConverter.convert(this?.airBurstStats),
                altShotgunStats = AltShotgunStatsResponseToDtoConveter.convert(this?.altShotgunStats),
                damageRanges = DamageRangeResponseToDtoConverter.convert(
                    this?.damageRanges ?: emptyList()
                ),
                altFireType = this?.altFireType ?: EMPTY_STRING,
                feature = this?.feature ?: EMPTY_STRING,
                fireMode = this?.fireMode ?: EMPTY_STRING,
                wallPenetration = this?.wallPenetration ?: EMPTY_STRING,
            )
        }
}

object AdsStatsResponseToDtoConverter : Converter<WeaponAdsStatsResponse?, WeaponAdsStatsDto> {

    override fun convert(from: WeaponAdsStatsResponse?): WeaponAdsStatsDto =
        with (from) {
            WeaponAdsStatsDto(
                burstCount = this?.burstCount ?: 0,
                fireRate = this?.fireRate ?: 0.0,
                firstBulletAccuracy = this?.firstBulletAccuracy ?: 0.0,
                runSpeedMultiplier = this?.runSpeedMultiplier ?: 0.0,
                zoomMultiplier = this?.zoomMultiplier ?: 0.0
            )
        }
}

object AirBurstStatsResponseToDtoConverter :
    Converter<WeaponAirBurstStatsResponse?, WeaponAirBurstStatsDto> {

    override fun convert(from: WeaponAirBurstStatsResponse?): WeaponAirBurstStatsDto =
        with (from) {
            WeaponAirBurstStatsDto(
                burstDistance = this?.burstDistance ?: 0.0,
                shotgunPelletCount = this?.shotgunPelletCount ?: 0,
            )
        }
}

object AltShotgunStatsResponseToDtoConveter:
    Converter<WeaponAltShotgunStatsResponse?, WeaponAltShotgunStatsDto> {

    override fun convert(from: WeaponAltShotgunStatsResponse?): WeaponAltShotgunStatsDto =
        with (from) {
            WeaponAltShotgunStatsDto(
                burstRate = this?.burstRate ?: 0.0,
                shotgunPelletCount = this?.shotgunPelletCount ?: 0
            )
        }
}

object DamageRangeResponseToDtoConverter:
    Converter<WeaponDamageRangeResponse?, WeaponDamageRangeDto> {

    override fun convert(from: WeaponDamageRangeResponse?): WeaponDamageRangeDto =
        with (from) {
            WeaponDamageRangeDto(
                bodyDamage = this?.bodyDamage ?: 0,
                headDamage = this?.headDamage ?: 0.0,
                legDamage = this?.legDamage ?: 0.0,
                rangeEndMeters = this?.rangeEndMeters ?: 0,
                rangeStartMeters = this?.rangeStartMeters ?: 0
            )
        }
}