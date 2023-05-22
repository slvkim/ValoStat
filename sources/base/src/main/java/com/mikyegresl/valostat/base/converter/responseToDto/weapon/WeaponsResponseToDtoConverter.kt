package com.mikyegresl.valostat.base.converter.responseToDto.weapon

import com.mikyegresl.valostat.base.common.converter.Converter
import com.mikyegresl.valostat.base.common.converter.Converter.Companion.EMPTY_STRING
import com.mikyegresl.valostat.base.model.weapon.WeaponDto
import com.mikyegresl.valostat.base.network.model.weapon.WeaponResponse
import com.mikyegresl.valostat.base.network.model.weapon.WeaponsResponse

object WeaponsResponseToDtoConverter : Converter<WeaponsResponse, List<WeaponDto>> {

    override fun convert(from: WeaponsResponse): List<WeaponDto> {
        val result = mutableListOf<WeaponDto>()

        from.data?.forEach { response ->
            result.add(
                WeaponDto(
                    uuid = response.uuid ?: EMPTY_STRING,
                    displayName = response.displayName ?: EMPTY_STRING,
                    category = mapCategory(response.category),
                    displayIcon = response.displayIcon ?: EMPTY_STRING,
                    stats = WeaponStatsRelatedResponseToDtoConverter.convert(response.stats),
                    skins = WeaponSkinRelatedResponseToDtoConverter.convert(response.skins),
                    shopData = WeaponShopDataResponseToDtoConverter.convert(response.shopData),
                )
            )
        }
        return result
    }

    private fun mapCategory(from: WeaponResponse.WeaponCategoryResponse?): WeaponDto.WeaponCategoryDto =
        when (from) {
            WeaponResponse.WeaponCategoryResponse.RIFLE -> WeaponDto.WeaponCategoryDto.RIFLE
            WeaponResponse.WeaponCategoryResponse.SHOTGUN -> WeaponDto.WeaponCategoryDto.SHOTGUN
            WeaponResponse.WeaponCategoryResponse.SIDEARM -> WeaponDto.WeaponCategoryDto.SIDEARM
            WeaponResponse.WeaponCategoryResponse.SNIPER -> WeaponDto.WeaponCategoryDto.SNIPER
            WeaponResponse.WeaponCategoryResponse.HEAVY -> WeaponDto.WeaponCategoryDto.HEAVY
            WeaponResponse.WeaponCategoryResponse.MELEE -> WeaponDto.WeaponCategoryDto.MELEE
            WeaponResponse.WeaponCategoryResponse.SMG -> WeaponDto.WeaponCategoryDto.SMG
            else -> WeaponDto.WeaponCategoryDto.UNKNOWN
        }
}