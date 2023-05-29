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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import com.mikyegresl.valostat.R
import com.mikyegresl.valostat.base.model.weapon.WeaponDto
import com.mikyegresl.valostat.base.model.weapon.skin.WeaponSkinChromaDto
import com.mikyegresl.valostat.base.model.weapon.stats.WeaponDamageRangeDto
import com.mikyegresl.valostat.base.model.weapon.stats.WeaponStatsDto
import com.mikyegresl.valostat.common.compose.ShowErrorState
import com.mikyegresl.valostat.common.compose.ShowLoadingState
import com.mikyegresl.valostat.features.player.BasicMediaPlayer
import com.mikyegresl.valostat.features.player.ComposablePlayerView
import com.mikyegresl.valostat.ui.dimen.ElemSize
import com.mikyegresl.valostat.ui.dimen.Padding
import com.mikyegresl.valostat.ui.theme.ValoStatTypography
import com.mikyegresl.valostat.ui.theme.playerBackgroundDark
import com.mikyegresl.valostat.ui.theme.secondaryBackgroundDark
import com.mikyegresl.valostat.ui.theme.secondaryTextDark
import com.mikyegresl.valostat.ui.theme.surfaceDark
import com.mikyegresl.valostat.ui.theme.washWhite
import com.mikyegresl.valostat.ui.widget.gradientModifier
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import kotlin.math.roundToInt

internal val LocalWeaponsDetailsViewModel = compositionLocalOf<WeaponDetailsViewModel?> { null }
internal val LocalProviderOfVideoPlayer = compositionLocalOf<BasicMediaPlayer?> { null }

private const val TAG = "WeaponsDetailsScreen"

@Composable
fun WeaponDetailsScreen(
    weaponId: String,
    viewModel: WeaponDetailsViewModel = koinViewModel {
        parametersOf(
            weaponId
        )
    },
    onBackPressed: () -> Unit
) {
    CompositionLocalProvider(LocalWeaponsDetailsViewModel provides viewModel) {
        val state = remember { viewModel.state }.collectAsStateWithLifecycle()

        when (val viewState = state.value) {
            is WeaponDetailsScreenState.WeaponDetailsDataState -> {
                WeaponDetailsAsDataState(
                    modifier = Modifier,
                    state = viewState
                ) {
                    onBackPressed()
                }
            }
            is WeaponDetailsScreenState.WeaponDetailsLoadingState -> {
                ShowLoadingState()
            }
            is WeaponDetailsScreenState.WeaponDetailsErrorState -> {
                ShowErrorState(errorMessage = viewState.t.message)

            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeaponDetailsAsDataState(
    modifier: Modifier = Modifier,
    state: WeaponDetailsScreenState.WeaponDetailsDataState,
    onBackPressed: () -> Unit
) {
    val viewModel = LocalWeaponsDetailsViewModel.current ?: return

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

            weaponSkins(
                state = state,
                chromas = state.skinsWithVideo,
                onVideoItemClicked = { chroma ->
                    viewModel.dispatchIntent(WeaponDetailsIntent.VideoClickedIntent(chroma))
                },
                onSkinLeftFocus = { chroma ->
                    viewModel.dispatchIntent(WeaponDetailsIntent.VideoDisposeIntent(chroma))
                }
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
    state: WeaponDetailsScreenState.WeaponDetailsDataState,
    chromas: List<WeaponSkinChromaDto>,
    onVideoItemClicked: (WeaponSkinChromaDto) -> Unit,
    onSkinLeftFocus: (WeaponSkinChromaDto) -> Unit
) : LazyListScope.() -> Unit = {

    item {
        Text(
            modifier = Modifier.padding(
                top = Padding.Dp16,
                start = Padding.Dp32,
                end = Padding.Dp32
            ),
            textAlign = TextAlign.Start,
            text = "${stringResource(id = R.string.skins)}:",
            style = ValoStatTypography.caption.copy(color = secondaryTextDark)
        )
    }
    chromas.forEachIndexed { i, item ->
        item {
            DisposableEffect(
                WeaponSkinItemContainer(
                    state = state,
                    chroma = item,
                    onVideoItemClicked = onVideoItemClicked
                )
            ) {
                onDispose {
                    onSkinLeftFocus(item)
                }
            }
            Spacer(modifier = Modifier.padding(vertical = Padding.Dp24))
        }
    }
}

@Composable
fun WeaponSkinItemContainer(
    modifier: Modifier = Modifier,
    state: WeaponDetailsScreenState.WeaponDetailsDataState,
    chroma: WeaponSkinChromaDto,
    onVideoItemClicked: (WeaponSkinChromaDto) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16 / 9f)
            .padding(horizontal = Padding.Dp32)
            .clickable { onVideoItemClicked(chroma) },
        contentAlignment = Alignment.Center
    ) {
        if (state.activeChroma != chroma) {
            WeaponSkinCardItem(chroma = chroma)
        } else {
            ComposablePlayerView(
                playerModifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                mediaUri = chroma.streamedVideo
            )
        }
    }
}

@Composable
fun WeaponSkinCardItem(
    modifier: Modifier = Modifier,
    chroma: WeaponSkinChromaDto
) {
    Column(
        modifier = modifier
    ) {
        Card(
            border = BorderStroke(1.dp, secondaryTextDark),
            backgroundColor = playerBackgroundDark
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(75f),
                    model = chroma.fullRenderPath,
                    contentScale = ContentScale.Inside,
                    contentDescription = chroma.displayName
                )
                Icon(
                    modifier = Modifier.size(ElemSize.Dp48),
                    painter = painterResource(id = R.drawable.ic_play_button),
                    contentDescription = stringResource(id = R.string.videos)
                )
            }
        }
        Text(
            modifier = Modifier
                .padding(top = Padding.Dp4, start = Padding.Dp4),
            text = chroma.displayName,
            style = ValoStatTypography.subtitle2,
            textAlign = TextAlign.Start,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}