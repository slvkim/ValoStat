package com.mikyegresl.valostat.features.agent.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.mikyegresl.valostat.R
import com.mikyegresl.valostat.base.model.ValoStatLocale
import com.mikyegresl.valostat.base.model.agent.AgentAbilityDto
import com.mikyegresl.valostat.base.model.agent.AgentOriginDto
import com.mikyegresl.valostat.base.model.agent.AgentRoleDto
import com.mikyegresl.valostat.base.model.agent.AgentVoiceLineDto
import com.mikyegresl.valostat.common.compose.ShowErrorState
import com.mikyegresl.valostat.common.compose.ShowLoadingState
import com.mikyegresl.valostat.features.agent.agentAbilitiesMock
import com.mikyegresl.valostat.features.player.exoplayer.newer.ComposablePlayerView
import com.mikyegresl.valostat.ui.dimen.ElemSize
import com.mikyegresl.valostat.ui.dimen.Padding
import com.mikyegresl.valostat.ui.theme.ValoStatTypography
import com.mikyegresl.valostat.ui.theme.mainTextDark
import com.mikyegresl.valostat.ui.theme.secondaryBackgroundDark
import com.mikyegresl.valostat.ui.theme.secondaryTextDark
import com.mikyegresl.valostat.ui.theme.surfaceDark
import com.mikyegresl.valostat.ui.theme.washWhite
import com.mikyegresl.valostat.ui.widget.gradientModifier
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

internal val LocalDetailsAgentsViewModel = compositionLocalOf<AgentDetailsViewModel?> { null }

@Preview
@Composable
fun PreviewAgentAbilityItem() {
    AgentAbilityItem(
        modifier = Modifier,
        ability = agentAbilitiesMock().first()
    )
}

@Composable
fun AgentDetailsScreen(
    agentId: String,
    locale: ValoStatLocale,
    viewModel: AgentDetailsViewModel = koinViewModel {
        parametersOf(
            agentId,
            locale
        )
    },
    onBackPressed: () -> Unit
) {
    CompositionLocalProvider(LocalDetailsAgentsViewModel provides viewModel) {
        val state = viewModel.state.collectAsStateWithLifecycle()

        when (val viewState = state.value) {
            is AgentDetailsScreenState.AgentDetailsDataState -> {
                AgentDetailsAsDataState(
                    modifier = Modifier,
                    state = viewState
                ) {
                    onBackPressed()
                }
            }
            is AgentDetailsScreenState.AgentDetailsLoadingState -> {
                ShowLoadingState()
            }
            is AgentDetailsScreenState.AgentDetailsErrorState -> {
                ShowErrorState(errorMessage = viewState.t.message)

            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AgentDetailsAsDataState(
    modifier: Modifier = Modifier,
    state: AgentDetailsScreenState.AgentDetailsDataState,
    onBackPressed: () -> Unit
) {
    val viewModel = LocalDetailsAgentsViewModel.current ?: return

    CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
        ) {
            item {
                AgentDetailsTopBar(
                    title = state.details.displayName,
                    imageSrc = state.details.fullPortrait
                ) {
                    onBackPressed()
                }
            }
            item {
                AgentDescriptionItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = Padding.Dp16,
                            start = Padding.Dp32,
                            end = Padding.Dp32
                        ),
                    state = state,
                    onPlayIconClicked = {
                        viewModel.dispatchIntent(AgentDetailsIntent.AudioClickedIntent(it))
                    }
                )
            }
            agentAbilities(
                abilities = state.details.abilities
            ).invoke(this)
        }
    }
}

@Composable
fun AgentDetailsTopBar(
    title: String,
    imageSrc: String,
    onBackPressed: () -> Unit
) {
    Column(
        modifier = gradientModifier(
            35f,
            .55f to surfaceDark,
            .55f to secondaryBackgroundDark
        )
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
                contentDescription = stringResource(id = R.string.agent_details),
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
            model = imageSrc,
            contentScale = ContentScale.FillBounds,
            contentDescription = title
        )
    }
}

@Composable
fun AgentDescriptionItem(
    modifier: Modifier = Modifier,
    state: AgentDetailsScreenState.AgentDetailsDataState,
    onPlayIconClicked: (AgentVoiceLineDto.VoiceLineMediaDto) -> Unit
) {
    val commonRowModifier = Modifier.fillMaxWidth()
    val dividerModifier = Modifier.padding(vertical = Padding.Dp8)

    Column(
        modifier = modifier
    ) {
        Spacer(Modifier.padding(vertical = Padding.Dp16))
        AgentOriginSection(
            modifier = commonRowModifier,
            origin = state.origin
        )
        Divider(
            modifier = dividerModifier,
            color = washWhite,
            thickness = 1.dp
        )
        AgentRoleSection(
            modifier = commonRowModifier,
            role = state.details.role
        )
        Divider(
            modifier = dividerModifier,
            color = washWhite,
            thickness = 1.dp
        )
        PointsForUltimateSection(
            modifier = commonRowModifier,
            pointsForUltimate = state.pointsForUltimate.toString()
        )
        Divider(
            modifier = dividerModifier,
            color = washWhite,
            thickness = 1.dp
        )
        VoiceLineSection(
            modifier = commonRowModifier,
            state = state,
            onPlayIconClicked = onPlayIconClicked
        )
        Divider(
            modifier = dividerModifier,
            color = washWhite,
            thickness = 1.dp
        )
        Text(
            text = state.details.description,
            style = ValoStatTypography.caption
        )
        Divider(
            modifier = dividerModifier,
            color = washWhite,
            thickness = 1.dp
        )
    }
}

@Composable
fun AgentOriginSection(
    modifier: Modifier = Modifier,
    origin: AgentOriginDto
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "${stringResource(id = R.string.origin)}:",
            style = ValoStatTypography.caption.copy(color = secondaryTextDark)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = origin.iconUrl,
                contentDescription = origin.countryName
            )
            Spacer(modifier = Modifier.padding(horizontal = Padding.Dp4))
            Text(
                text = origin.countryName,
                style = ValoStatTypography.caption
            )
        }
    }
}

@Composable
fun AgentRoleSection(
    modifier: Modifier = Modifier,
    role: AgentRoleDto
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "${stringResource(id = R.string.role)}:",
            style = ValoStatTypography.caption.copy(color = secondaryTextDark)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier.size(ElemSize.Dp16),
                model = role.displayIcon,
                contentScale = ContentScale.Fit,
                contentDescription = stringResource(id = R.string.role)
            )
            Spacer(modifier = Modifier.padding(horizontal = Padding.Dp4))

            Text(
                text = role.displayName,
                style = ValoStatTypography.caption
            )
        }
    }
}

@Composable
fun PointsForUltimateSection(
    modifier: Modifier = Modifier,
    pointsForUltimate: String,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "${stringResource(id = R.string.points_for_ulti)}:",
            style = ValoStatTypography.caption.copy(color = secondaryTextDark)
        )
        Text(
            text = pointsForUltimate,
            style = ValoStatTypography.caption
        )
    }
}

@Composable
fun VoiceLineSection(
    modifier: Modifier = Modifier,
    state: AgentDetailsScreenState.AgentDetailsDataState,
    onPlayIconClicked: (AgentVoiceLineDto.VoiceLineMediaDto) -> Unit
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "${stringResource(id = R.string.voiceline)}:",
            style = ValoStatTypography.caption.copy(color = secondaryTextDark)
        )
        VoiceLineItemContainer(
            state = state,
            onPlayIconClicked = onPlayIconClicked
        )
    }
}

@Composable
fun VoiceLineItemContainer(
    state: AgentDetailsScreenState.AgentDetailsDataState,
    onPlayIconClicked: (AgentVoiceLineDto.VoiceLineMediaDto) -> Unit
) {
    if (state.activeVoiceline != state.details.voiceLine.voiceline) {
        VoiceLineIconItem(
            voiceline = state.details.voiceLine.voiceline,
            onPlayIconClicked = onPlayIconClicked
        )
    } else {
        ComposablePlayerView(
            playerModifier = Modifier.wrapContentSize(),
            mediaUri = state.details.voiceLine.voiceline.wave,
            isVideoPlayer = false
        )
    }

}

@Composable
fun VoiceLineIconItem(
    voiceline: AgentVoiceLineDto.VoiceLineMediaDto,
    onPlayIconClicked: (AgentVoiceLineDto.VoiceLineMediaDto) -> Unit
) {
    Icon(
        modifier = Modifier.clickable {
            onPlayIconClicked(voiceline)
        },
        painter = painterResource(id = R.drawable.ic_play_button),
        contentDescription = stringResource(id = R.string.voiceline)
    )
}

fun agentAbilities(
    abilities: List<AgentAbilityDto>
) : LazyListScope.() -> Unit = {
    item {
        Text(
            modifier = Modifier.padding(vertical = Padding.Dp16, horizontal = Padding.Dp32),
            text = stringResource(id = R.string.abilities),
            style = ValoStatTypography.caption.copy(color = secondaryTextDark)
        )
    }
    abilities.forEach {
        item {
            AgentAbilityItem(ability = it)
            Spacer(modifier = Modifier.padding(vertical = Padding.Dp16))
        }
    }
}

@Composable
fun AgentAbilityItem(
    modifier: Modifier = Modifier,
    ability: AgentAbilityDto
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Padding.Dp32),
        border = BorderStroke(1.dp, secondaryTextDark)
    ) {
        Column(
            modifier = Modifier.padding(Padding.Dp16)
        ) {
            Text(
                text = ability.displayName,
                style = ValoStatTypography.caption.copy(color = secondaryTextDark)
            )
            Row(
                modifier = Modifier.padding(Padding.Dp8),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = Modifier.size(ElemSize.Dp36),
                    model = ability.displayIcon,
                    contentDescription = ability.displayName
                )
                Spacer(modifier = Modifier.padding(horizontal = Padding.Dp4))
                Text(
                    text = "${stringResource(id = R.string.type)}: ",
                    style = ValoStatTypography.caption.copy(color = secondaryTextDark)
                )
                Text(
                    text = ability.slot,
                    style = ValoStatTypography.subtitle2
                )
            }
            Text(
                text = buildAnnotatedString {
                    val descCaption = "${stringResource(id = R.string.description)}: "
                    withStyle(
                        style = SpanStyle(
                            color = secondaryTextDark,
                            fontSize = 14.sp
                        )
                    ) {
                        append(descCaption)
                    }
                    withStyle(
                        style = SpanStyle(
                            color = mainTextDark,
                            fontSize = 13.sp
                        )
                    ) {
                        append(ability.description)
                    }
                },
                style = ValoStatTypography.caption
            )
        }
    }
}