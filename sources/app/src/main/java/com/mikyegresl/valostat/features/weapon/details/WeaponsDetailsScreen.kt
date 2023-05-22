package com.mikyegresl.valostat.features.weapon.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mikyegresl.valostat.R
import com.mikyegresl.valostat.base.model.weapon.WeaponDto
import com.mikyegresl.valostat.base.model.weapon.skin.WeaponSkinChromaDto
import com.mikyegresl.valostat.base.model.weapon.skin.WeaponSkinDto
import com.mikyegresl.valostat.base.model.weapon.stats.WeaponDamageRangeDto
import com.mikyegresl.valostat.base.model.weapon.stats.WeaponStatsDto
import com.mikyegresl.valostat.ui.dimen.Padding
import com.mikyegresl.valostat.ui.theme.ValoStatTypography
import com.mikyegresl.valostat.ui.theme.secondaryBackgroundDark
import com.mikyegresl.valostat.ui.theme.secondaryTextDark
import com.mikyegresl.valostat.ui.theme.surfaceDark
import com.mikyegresl.valostat.ui.theme.washWhite
import com.mikyegresl.valostat.ui.widget.gradientModifier
import kotlin.math.roundToInt

@Composable
fun WeaponDetailsScreen(
    state: WeaponDetailsScreenState?,
    onBackPressed: () -> Unit
) {
    if (state == null) return
    WeaponDetailsAsDataState(
        modifier = Modifier,
        state = state
    ) {
        onBackPressed()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeaponDetailsAsDataState(
    modifier: Modifier = Modifier,
    state: WeaponDetailsScreenState,
    onBackPressed: () -> Unit
) {
    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
        ) {
            item {
                Column(
                    modifier = gradientModifier(
                        40f,
                        .65f to surfaceDark,
                        .65f to secondaryBackgroundDark
                    )
                ) {
                    WeaponDetailsTopBar(
                        title = state.details.displayName,
                        imageSrc = state.details.displayIcon
                    ) {
                        onBackPressed()
                    }
                    WeaponDescriptionItem(
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                top = Padding.Dp16,
                                start = Padding.Dp32,
                                end = Padding.Dp32
                            ),
                        state.details
                    )
                }
            }
            weaponDamageRanges(stats = state.details.stats).invoke(this)

            weaponSkins(skins = state.details.skins).invoke(this)

            skinVideos(
                chromas = state.skinsWithVideo,
                onVideoItemClicked = {}
            ).invoke(this)
        }
    }
}

@Composable
fun WeaponDetailsTopBar(
    title: String,
    imageSrc: String,
    onBackPressed: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = Padding.Dp24,
                    start = Padding.Dp24,
                    end = Padding.Dp24
                ),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .rotate(210f)
                    .clickable { onBackPressed() },
                painter = painterResource(id = R.drawable.ic_arrow_left),
                contentDescription = stringResource(id = R.string.weapon_details),
                tint = secondaryTextDark
            )
            Text(
                modifier = Modifier
                    .weight(1f),
                text = title,
                textAlign = TextAlign.Center,
                style = ValoStatTypography.subtitle1
            )
        }
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(Padding.Dp24),
            model = imageSrc,
            contentScale = ContentScale.FillWidth,
            contentDescription = title
        )
    }
}

@Composable
fun WeaponDescriptionItem(
    modifier: Modifier = Modifier,
    details: WeaponDto
) {
    Column(
        modifier = modifier
    ) {
        Spacer(Modifier.padding(vertical = Padding.Dp16))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${stringResource(id = R.string.type)}:",
                style = ValoStatTypography.caption.copy(color = secondaryTextDark)
            )
            Text(
                text = details.category.title,
                style = ValoStatTypography.subtitle2
            )
        }
        Divider(
            modifier = Modifier.padding(vertical = Padding.Dp8),
            color = washWhite,
            thickness = 1.dp
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${stringResource(id = R.string.creds)}:",
                style = ValoStatTypography.caption.copy(color = secondaryTextDark)
            )
            Text(
                text = details.shopData.cost.toString(),
                style = ValoStatTypography.subtitle2
            )
        }
        Divider(
            modifier = Modifier.padding(vertical = Padding.Dp8),
            color = washWhite,
            thickness = 1.dp
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${stringResource(id = R.string.magazine)}:",
                style = ValoStatTypography.caption.copy(color = secondaryTextDark)
            )
            Text(
                text = details.stats.magazineSize.toString(),
                style = ValoStatTypography.subtitle2
            )
        }
        Divider(
            modifier = Modifier.padding(vertical = Padding.Dp8),
            color = washWhite,
            thickness = 1.dp
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${stringResource(id = R.string.fire_rate)}:",
                style = ValoStatTypography.caption.copy(color = secondaryTextDark)
            )
            Text(
                text = "${details.stats.fireRate} rounds/sec",
                style = ValoStatTypography.subtitle2
            )
        }
        Divider(
            modifier = Modifier.padding(vertical = Padding.Dp8),
            color = washWhite,
            thickness = 1.dp
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${stringResource(id = R.string.fire_mode)}:",
                style = ValoStatTypography.caption.copy(color = secondaryTextDark)
            )
            Text(
                text = details.stats.fireMode.title,
                style = ValoStatTypography.subtitle2
            )
        }
        Divider(
            modifier = Modifier.padding(vertical = Padding.Dp8),
            color = washWhite,
            thickness = 1.dp
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${stringResource(id = R.string.alt_fire_mode)}:",
                style = ValoStatTypography.caption.copy(color = secondaryTextDark)
            )
            Text(
                text = details.stats.altFireType.title,
                style = ValoStatTypography.subtitle2
            )
        }
        Divider(
            modifier = Modifier.padding(vertical = Padding.Dp8),
            color = washWhite,
            thickness = 1.dp
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${stringResource(id = R.string.wall_penetration)}:",
                style = ValoStatTypography.caption.copy(color = secondaryTextDark)
            )
            Text(
                text = details.stats.wallPenetration.title,
                style = ValoStatTypography.subtitle2
            )
        }
        Divider(
            modifier = Modifier.padding(vertical = Padding.Dp8),
            color = washWhite,
            thickness = 1.dp
        )
    }
}

fun weaponDamageRanges(
    modifier: Modifier = Modifier,
    stats: WeaponStatsDto
) : LazyListScope.() -> Unit = {
    item {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Padding.Dp16, horizontal = Padding.Dp32),
            text = "${stringResource(id = R.string.damage)}:",
            style = ValoStatTypography.caption.copy(color = secondaryTextDark),
            textAlign = TextAlign.Center
        )
    }
    item {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = Padding.Dp32),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DamageRangeHeader()
            stats.damageRanges.forEach {
                Spacer(modifier = Modifier.padding(vertical = Padding.Dp4))
                DamageRangeItem(damage = it)
            }
            Divider(
                modifier = Modifier.padding(vertical = Padding.Dp8),
                color = washWhite,
                thickness = 1.dp
            )
        }
    }
}

@Composable
fun DamageRangeHeader(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.range),
            textAlign = TextAlign.Start,
            style = ValoStatTypography.caption.copy(color = secondaryTextDark)
        )
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.head),
            style = ValoStatTypography.caption.copy(color = secondaryTextDark)
        )
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.body),
            style = ValoStatTypography.caption.copy(color = secondaryTextDark)
        )
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End,
            text = stringResource(id = R.string.legs),
            style = ValoStatTypography.caption.copy(color = secondaryTextDark)
        )
    }
}

@Composable
fun DamageRangeItem(
    modifier: Modifier = Modifier,
    damage: WeaponDamageRangeDto
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Start,
            text = "${damage.rangeStartMeters}-${damage.rangeEndMeters}",
            style = ValoStatTypography.subtitle2
        )
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            text = "${damage.headDamage.roundToInt()}",
            style = ValoStatTypography.subtitle2
        )
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            text = "${damage.bodyDamage}",
            style = ValoStatTypography.subtitle2
        )
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End,
            text = "${damage.legDamage.roundToInt()}",
            style = ValoStatTypography.subtitle2
        )
    }
}

fun weaponSkins(
    skins: List<WeaponSkinDto>
) : LazyListScope.() -> Unit = {
    item {
        Text(
            modifier = Modifier.padding(vertical = Padding.Dp16, horizontal = Padding.Dp32),
            textAlign = TextAlign.Start,
            text = "${stringResource(id = R.string.skins)}:",
            style = ValoStatTypography.caption.copy(color = secondaryTextDark)
        )
    }
    item {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Padding.Dp16)
        ) {
            itemsIndexed(skins) { i, skin ->
                WeaponSkinItem(skin = skin)
                if (i == skins.lastIndex) {
                    Spacer(modifier = Modifier.padding(horizontal = Padding.Dp24))
                } else {
                    Spacer(modifier = Modifier.padding(horizontal = Padding.Dp8))
                }
            }
        }
        Spacer(modifier = Modifier.padding(horizontal = Padding.Dp16))
    }
}

@Composable
fun WeaponSkinItem(
    modifier: Modifier = Modifier,
    skin: WeaponSkinDto
) {
    val screenConfig = LocalConfiguration.current
    val minItemWidth by remember {
        mutableStateOf(screenConfig.screenWidthDp/3)
    }
    Column(
        modifier = modifier.width(minItemWidth.dp)
    ) {
        Card(
            border = BorderStroke(1.dp, secondaryTextDark)
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(minItemWidth.dp)
                    .padding(Padding.Dp16),
                model = skin.iconPath,
                contentScale = ContentScale.Inside,
                contentDescription = skin.displayName
            )
        }
        Text(
            modifier = Modifier
                .padding(top = Padding.Dp4, start = Padding.Dp4),
            text = skin.displayName,
            style = ValoStatTypography.subtitle2,
            textAlign = TextAlign.Start,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

fun skinVideos(
    chromas: List<WeaponSkinChromaDto>,
    onVideoItemClicked: (String) -> Unit
) : LazyListScope.() -> Unit = {
    chromas.forEach {
        item {
            SkinVideoItem(
                chroma = it,
                onVideoItemClicked = onVideoItemClicked
            )
        }
    }
}

@Composable
fun SkinVideoItem(
    modifier: Modifier = Modifier,
    chroma: WeaponSkinChromaDto,
    onVideoItemClicked: (String) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onVideoItemClicked(chroma.streamedVideo) },
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = chroma.iconPath,
            contentDescription = chroma.displayName
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_videos),
            contentDescription = stringResource(id = R.string.videos))
    }
}