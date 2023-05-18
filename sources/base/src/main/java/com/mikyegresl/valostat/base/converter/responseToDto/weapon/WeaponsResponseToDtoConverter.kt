package com.mikyegresl.valostat.base.converter.responseToDto.weapon

import com.mikyegresl.valostat.base.common.converter.Converter
import com.mikyegresl.valostat.base.common.converter.Converter.Companion.EMPTY_STRING
import com.mikyegresl.valostat.base.model.weapon.WeaponDto
import com.mikyegresl.valostat.base.network.model.weapon.WeaponsResponse

object WeaponsResponseToDtoConverter : Converter<WeaponsResponse, List<WeaponDto>> {

    override fun convert(from: WeaponsResponse): List<WeaponDto> {
        val result = mutableListOf<WeaponDto>()

        from.data?.forEach { response ->
            result.add(
                WeaponDto(
                    uuid = response.uuid ?: EMPTY_STRING,
                    name = response.name ?: EMPTY_STRING,
                    category = response.category ?: EMPTY_STRING,
                    assetPath = response.assetPath ?: EMPTY_STRING,
                    iconPath = response.iconPath ?: EMPTY_STRING,
                    killStreamIcon = response.killStreamIcon ?: EMPTY_STRING,
                    stats = WeaponStatsRelatedResponseToDtoConverter.convert(response.stats),
                    skins = WeaponSkinRelatedResponseToDtoConverter.convert(response.skins),
                    shopData = WeaponShopDataResponseToDtoConverter.convert(response.shopData),
                )
            )
        }
        return result
    }
}