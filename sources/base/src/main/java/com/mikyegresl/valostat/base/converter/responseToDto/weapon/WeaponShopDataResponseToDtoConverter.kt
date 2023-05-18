package com.mikyegresl.valostat.base.converter.responseToDto.weapon

import com.mikyegresl.valostat.base.common.converter.Converter
import com.mikyegresl.valostat.base.common.converter.Converter.Companion.EMPTY_STRING
import com.mikyegresl.valostat.base.model.weapon.shop.WeaponShopDataDto
import com.mikyegresl.valostat.base.model.weapon.shop.WeaponShopGridPositionDto
import com.mikyegresl.valostat.base.network.model.weapon.shop.WeaponShopDataResponse
import com.mikyegresl.valostat.base.network.model.weapon.shop.WeaponShopGridPositionResponse

object WeaponShopDataResponseToDtoConverter :
    Converter<WeaponShopDataResponse?, WeaponShopDataDto> {

    override fun convert(from: WeaponShopDataResponse?): WeaponShopDataDto =
        with (from) {
            WeaponShopDataDto(
                categoryText = this?.categoryText ?: EMPTY_STRING,
                cost = this?.cost ?: 0,
                gridPosition = ShopGridPositionResponseToDtoConverter.convert(this?.gridPosition),
                image = this?.image ?: EMPTY_STRING,
                newImage = this?.newImage ?: EMPTY_STRING,
                newImage2 = this?.newImage2 ?: EMPTY_STRING,
                assetPath = this?.assetPath ?: EMPTY_STRING,
                canBeTrashed = this?.canBeTrashed ?: false,
                category = this?.category ?: EMPTY_STRING,
            )
        }
}

object ShopGridPositionResponseToDtoConverter:
    Converter<WeaponShopGridPositionResponse?, WeaponShopGridPositionDto> {

    override fun convert(from: WeaponShopGridPositionResponse?): WeaponShopGridPositionDto =
        with (from) {
            WeaponShopGridPositionDto(
                column = this?.column ?: 0,
                row = this?.row ?: 0,
            )
        }
}