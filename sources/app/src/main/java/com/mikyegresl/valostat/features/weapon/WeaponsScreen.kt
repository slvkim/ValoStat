package com.mikyegresl.valostat.features.weapon

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.ImageLoader
import coil.compose.AsyncImage
import com.mikyegresl.valostat.R
import com.mikyegresl.valostat.base.model.weapon.WeaponDto
import com.mikyegresl.valostat.base.model.weapon.shop.WeaponShopDataDto
import com.mikyegresl.valostat.base.model.weapon.shop.WeaponShopGridPositionDto
import com.mikyegresl.valostat.base.model.weapon.skin.WeaponSkinDto
import com.mikyegresl.valostat.base.model.weapon.stats.WeaponAdsStatsDto
import com.mikyegresl.valostat.base.model.weapon.stats.WeaponDamageRangeDto
import com.mikyegresl.valostat.base.model.weapon.stats.WeaponStatsDto
import com.mikyegresl.valostat.common.compose.ShowErrorState
import com.mikyegresl.valostat.common.compose.ShowLoadingState
import com.mikyegresl.valostat.ui.dimen.Padding
import com.mikyegresl.valostat.ui.theme.ValoStatTypography
import com.mikyegresl.valostat.ui.theme.mainTextDark
import com.mikyegresl.valostat.ui.theme.secondaryBackgroundDark
import com.mikyegresl.valostat.ui.theme.secondaryTextDark
import kotlinx.coroutines.flow.StateFlow

@Preview
@Composable
fun PreviewWeaponsScreenTopBar() {
    WeaponsScreenTopBar()
}

@Preview
@Composable
fun PreviewWeaponItem(

) {
    WeaponItem(weapon = weaponItemMock(), onClick = {})
}

@Composable
fun WeaponsScreen(
    weaponsScreenState: StateFlow<WeaponsScreenState>,
    onClick: (String) -> Unit
) {
    val viewState = weaponsScreenState.collectAsStateWithLifecycle()
    val state = viewState.value

    Scaffold(
        modifier = Modifier,
        topBar = {
            WeaponsScreenTopBar()
        }
    ) { paddingValues ->
        when (state) {
            is WeaponsScreenState.WeaponsScreenLoadingState -> {
                ShowLoadingState()
            }

            is WeaponsScreenState.WeaponsScreenErrorState -> {
                ShowErrorState(errorMessage = state.t.message)
            }
            is WeaponsScreenState.WeaponsScreenDataState -> {
                ShowWeaponsScreenAsDataState(
                    modifier = Modifier.padding(paddingValues),
                    state = state,
                    onClick = onClick
                )
            }
        }
    }
}

@Composable
fun ShowWeaponsScreenAsDataState(
    modifier: Modifier = Modifier,
    state: WeaponsScreenState.WeaponsScreenDataState,
    onClick: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        WeaponsList(
            modifier = Modifier,
            weapons = state.weapons,
            onClick = onClick
        )
    }
}

@Composable
fun WeaponsScreenTopBar(
    modifier: Modifier = Modifier,
    pageTitle: String = stringResource(id = R.string.weapons)
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Padding.Dp16, vertical = Padding.Dp16),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = modifier
                .wrapContentWidth()
                .weight(1f),
            text = pageTitle,
            style = ValoStatTypography.h6
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeaponsList(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    weapons: List<WeaponDto>,
    onClick: (String) -> Unit
) {
    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null
    ) {
        LazyColumn(
            modifier = modifier,
            state = listState,
            contentPadding = PaddingValues(Padding.Dp8),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(weapons) { weapon ->
                WeaponItem(weapon = weapon, onClick = onClick)
                Spacer(modifier = Modifier.padding(vertical = Padding.Dp8))
            }
        }
    }
}

@Composable
fun WeaponItem(
    modifier: Modifier = Modifier,
    weapon: WeaponDto,
    onClick: (String) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = Padding.Dp16)
            .clickable { onClick(weapon.uuid) },
        border = BorderStroke(1.dp, color = secondaryTextDark)
    ) {
        Column {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 104.dp, max = 120.dp),
                model = weapon.displayIcon,
                imageLoader = ImageLoader(LocalContext.current),
                contentScale = ContentScale.Inside,
                contentDescription = stringResource(id = R.string.weapons)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(secondaryBackgroundDark),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(start = Padding.Dp8),
                    text = weapon.displayName.uppercase(),
                    style = ValoStatTypography.subtitle2
                )
                Icon(
                    modifier = Modifier
                        .padding(end = Padding.Dp8)
                        .rotate(30f),
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    contentDescription = stringResource(id = R.string.weapons),
                    tint = mainTextDark
                )
            }

        }
    }
}

private fun weaponItemMock(): WeaponDto =
    WeaponDto(
        uuid = "9c82e19d-4575-0200-1a81-3eacf00cf872",
        displayName = "Vandal",
        category = WeaponDto.WeaponCategoryDto.RIFLE,
        displayIcon = "27f21d97-4c4b-bd1c-1f08-31830ab0be84",
        stats = weaponStatsMock(),
        skins = weaponSkinsMock(),
        shopData = weaponShopDataMock(),
    )

private fun weaponStatsMock(): WeaponStatsDto =
    WeaponStatsDto(
        equipTimeSeconds = 1.0,
        fireRate = 9.75,
        firstBulletAccuracy = 0.25,
        magazineSize = 25,
        reloadTimeSeconds = 2.5,
        runSpeedMultiplier = 0.8,
        shotgunPelletCount = 1,
        adsStats = WeaponAdsStatsDto(
            burstCount = 1,
            fireRate = 8.775,
            firstBulletAccuracy = 0.1575,
            runSpeedMultiplier = 0.76,
            zoomMultiplier = 1.25,
        ),
        airBurstStats = null,
        altShotgunStats = null,
        damageRanges = listOf(
            WeaponDamageRangeDto(
                bodyDamage = 40,
                headDamage = 160.0,
                legDamage = 34.0,
                rangeEndMeters = 0,
                rangeStartMeters = 50
            )
        ),
        altFireType = WeaponStatsDto.AltFireTypeDto.AIR_BURST,
        feature = WeaponStatsDto.WeaponFeatureDto.SILENCED,
        fireMode = WeaponStatsDto.FireModeDto.SEMI_AUTOMATIC,
        wallPenetration = WeaponStatsDto.WallPenetrationDto.HIGH,
    )

private fun weaponSkinsMock(): List<WeaponSkinDto> =
    listOf(

    )

private fun weaponShopDataMock(): WeaponShopDataDto =
    WeaponShopDataDto(
        categoryText = "Assault Rifles",
        cost = 2900,
        gridPosition = WeaponShopGridPositionDto(2, 3),
        image = "",
        newImage = "https://media.valorant-api.com/weapons/9c82e19d-4575-0200-1a81-3eacf00cf872/shop/newimage.png",
        newImage2 = "",
        assetPath = "ShooterGame/Content/Equippables/Guns/Rifles/AK/AK47WeaponPurchase",
        canBeTrashed = true,
        category = "Rifles",
    )