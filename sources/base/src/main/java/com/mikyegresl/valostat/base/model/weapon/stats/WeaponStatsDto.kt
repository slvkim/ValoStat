package com.mikyegresl.valostat.base.model.weapon.stats

import android.os.Parcelable
import androidx.annotation.StringRes
import com.mikyegresl.valostat.base.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeaponStatsDto(
    val equipTimeSeconds: Double,
    val fireRate: Double,
    val firstBulletAccuracy: Double,
    val magazineSize: Int,
    val reloadTimeSeconds: Double,
    val runSpeedMultiplier: Double,
    val shotgunPelletCount: Int,
    val adsStats: WeaponAdsStatsDto,
    val airBurstStats: WeaponAirBurstStatsDto?,
    val altShotgunStats: WeaponAltShotgunStatsDto?,
    val damageRanges: List<WeaponDamageRangeDto>,
    val wallPenetration: WallPenetrationDto,
    val feature: WeaponFeatureDto,
    val fireMode: FireModeDto,
    val altFireType: AltFireTypeDto
) : Parcelable {
    enum class WallPenetrationDto(@StringRes val titleRes: Int) {
        HIGH(R.string.high),
        MEDIUM(R.string.medium),
        LOW(R.string.low),
        UNKNOWN(R.string.unknown)
    }

    enum class WeaponFeatureDto(@StringRes val titleRes: Int) {
        SILENCED(R.string.silenced),
        DUAL_ZOOM(R.string.dual_zoom),
        ROF_INCREASE(R.string.rof_increase),
        UNKNOWN(R.string.unknown)
    }

    enum class FireModeDto(@StringRes val titleRes: Int) {
        SEMI_AUTOMATIC(R.string.semi_automatic),
        UNKNOWN(R.string.unknown)
    }

    enum class AltFireTypeDto(@StringRes val titleRes: Int) {
        ADS(R.string.ads),
        AIR_BURST(R.string.air_burst),
        SHOTGUN(R.string.shotgun),
        UNKNOWN(R.string.unknown)
    }
}