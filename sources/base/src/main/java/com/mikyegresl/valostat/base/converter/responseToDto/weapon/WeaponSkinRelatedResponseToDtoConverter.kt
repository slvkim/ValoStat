package com.mikyegresl.valostat.base.converter.responseToDto.weapon

import com.mikyegresl.valostat.base.common.converter.Converter
import com.mikyegresl.valostat.base.common.converter.Converter.Companion.EMPTY_STRING
import com.mikyegresl.valostat.base.model.weapon.skin.WeaponSkinChromaDto
import com.mikyegresl.valostat.base.model.weapon.skin.WeaponSkinDto
import com.mikyegresl.valostat.base.model.weapon.skin.WeaponSkinLevelDto
import com.mikyegresl.valostat.base.network.model.weapon.skin.WeaponSkinChromaResponse
import com.mikyegresl.valostat.base.network.model.weapon.skin.WeaponSkinLevelResponse
import com.mikyegresl.valostat.base.network.model.weapon.skin.WeaponSkinResponse

object WeaponSkinRelatedResponseToDtoConverter : Converter<WeaponSkinResponse?, WeaponSkinDto> {

    override fun convert(from: WeaponSkinResponse?): WeaponSkinDto =
        with(from) {
            WeaponSkinDto(
                uuid = this?.uuid ?: EMPTY_STRING,
                themeUuid = this?.themeUuid ?: EMPTY_STRING,
                contentTierUuid = this?.contentTierUuid ?: EMPTY_STRING,
                name = this?.name ?: EMPTY_STRING,
                iconPath = this?.iconPath ?: EMPTY_STRING,
                wallpaperPath = this?.wallpaperPath ?: EMPTY_STRING,
                assetPath = this?.assetPath ?: EMPTY_STRING,
                chromas = SkinChromaResponseToDtoConveter.convert(this?.chromas),
                levels = SkinLevelResponseToDtoConverter.convert(this?.levels)
            )
        }
}

object SkinChromaResponseToDtoConveter : Converter<WeaponSkinChromaResponse?, WeaponSkinChromaDto> {

    override fun convert(from: WeaponSkinChromaResponse?): WeaponSkinChromaDto =
        with(from) {
            WeaponSkinChromaDto(
                uuid = this?.uuid ?: EMPTY_STRING,
                name = this?.name ?: EMPTY_STRING,
                assetPath = this?.assetPath ?: EMPTY_STRING,
                iconPath = this?.iconPath ?: EMPTY_STRING,
                fullRenderPath = this?.fullRenderPath ?: EMPTY_STRING,
                swatchPath = this?.swatchPath ?: EMPTY_STRING,
                streamedVideo = this?.streamedVideo ?: EMPTY_STRING
            )
        }
}

object SkinLevelResponseToDtoConverter : Converter<WeaponSkinLevelResponse?, WeaponSkinLevelDto> {

    override fun convert(from: WeaponSkinLevelResponse?): WeaponSkinLevelDto =
        with(from) {
            WeaponSkinLevelDto(
                uuid = this?.uuid ?: EMPTY_STRING,
                name = this?.name ?: EMPTY_STRING,
                assetPath = this?.assetPath ?: EMPTY_STRING,
                iconPath = this?.iconPath ?: EMPTY_STRING,
                levelItem = this?.levelItem ?: EMPTY_STRING,
                streamedVideo = this?.streamedVideo ?: EMPTY_STRING
            )
        }
}