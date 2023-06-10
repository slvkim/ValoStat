package com.mikyegresl.valostat.features.agent

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.mikyegresl.valostat.R
import com.mikyegresl.valostat.base.model.agent.AgentAbilityDto
import com.mikyegresl.valostat.base.model.agent.AgentDto
import com.mikyegresl.valostat.base.model.agent.AgentOriginDto
import com.mikyegresl.valostat.base.model.agent.AgentRoleDto
import com.mikyegresl.valostat.base.model.agent.AgentVoiceLineDto
import com.mikyegresl.valostat.common.compose.ShowErrorState
import com.mikyegresl.valostat.common.compose.ShowLoadingState
import com.mikyegresl.valostat.common.compose.vertical
import com.mikyegresl.valostat.ui.dimen.Padding
import com.mikyegresl.valostat.ui.theme.ValoStatTypography
import com.mikyegresl.valostat.ui.theme.abilitiesBackgroundDark
import com.mikyegresl.valostat.ui.theme.secondaryTextDark
import kotlinx.coroutines.flow.StateFlow

private const val TAG = "AgentsScreen"

@Preview
@Composable
fun PreviewAgentItem() {
    AgentItem(
        agent = agentMock(),
        onAgentClicked = {}
    )
}

@Composable
fun AgentsScreen(
    agentsScreenState: StateFlow<AgentsScreenState>,
    onAgentClicked: (String) -> Unit
) {
    val viewState = agentsScreenState.collectAsStateWithLifecycle()
    val state = viewState.value

    Scaffold(
        modifier = Modifier,
        topBar = {
            AgentsScreenTopBar()
        }
    ) { paddingValues ->
        when (state) {
            is AgentsScreenState.AgentsScreenLoadingState -> {
                ShowLoadingState()
            }
            is AgentsScreenState.AgentsScreenErrorState -> {
                ShowErrorState(errorMessage = state.t.message)
            }
            is AgentsScreenState.AgentsScreenDataState -> {
                AgentsScreenAsDataState(
                    modifier = Modifier.padding(paddingValues),
                    state = state,
                    onAgentClicked = onAgentClicked
                )
            }
        }
    }
}

@Composable
fun AgentsScreenTopBar(
    modifier: Modifier = Modifier,
    pageTitle: String = stringResource(id = R.string.agents)
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

@Composable
fun AgentsScreenAsDataState(
    modifier: Modifier = Modifier,
    state: AgentsScreenState.AgentsScreenDataState,
    onAgentClicked: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        AgentsList(
            modifier = Modifier,
            agents = state.agents,
        ) {
            onAgentClicked(it)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AgentsList(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    agents: List<AgentDto>,
    onAgentClicked: (String) -> Unit
) {
    val configuration = LocalConfiguration.current
    val widgetHeight by remember {
        mutableStateOf((configuration.screenHeightDp / 1.5).dp)
    }
    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null
    ) {
        LazyRow(
            modifier = modifier
                .fillMaxWidth()
                .height(widgetHeight)
                .padding(top = Padding.Dp32),
            state = listState,
            contentPadding = PaddingValues(Padding.Dp8),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(agents) { agent ->
                Spacer(modifier = Modifier.padding(horizontal = Padding.Dp16))
                AgentItem(
                    agent = agent,
                ) {
                    onAgentClicked(it)
                }
                Spacer(modifier = Modifier.padding(horizontal = Padding.Dp16))
            }
        }
    }
}

@Composable
fun AgentItem(
    modifier: Modifier = Modifier,
    agent: AgentDto,
    onAgentClicked: (String) -> Unit
) {
    val context = LocalContext.current
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val containerMinWidth by remember {
        mutableStateOf((screenWidth / 1.6).dp)
    }
    val containerMaxWidth by remember {
        mutableStateOf((screenWidth / 1.4).dp)
    }
    val colors = agent.backgroundGradientColors.map {
        Color(java.lang.Long.parseLong(it, 16))
    }

    Column(
        modifier = modifier
            .widthIn(containerMinWidth, containerMaxWidth)
            .clickable { onAgentClicked(agent.uuid) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(4.5f)
                .background(Brush.linearGradient(colors)),
            contentAlignment = Alignment.BottomStart
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxWidth(),
                model = ImageRequest.Builder(context)
                    .data(agent.displayIcon)
                    .build(),
                loading = {
                    CircularProgressIndicator(
                        modifier = Modifier.wrapContentSize(),
                        color = secondaryTextDark
                    )
                },
                contentScale = ContentScale.FillWidth,
                contentDescription = stringResource(id = R.string.agents)
            )
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(top = Padding.Dp24)
                    .offset(x = -(Padding.Dp32)),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    modifier = Modifier
                        .vertical()
                        .rotate(-270f)
                        .offset(y = -(20).dp),
                    text = agent.displayName,
                    style = ValoStatTypography.h3.copy(
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(x = 2f, y = 4f),
                            blurRadius = 0.2f
                        )
                    )
                )
                Text(
                    modifier = Modifier
                        .vertical()
                        .padding(top = Padding.Dp4)
                        .rotate(-270f),
                    text = agent.role.displayName,
                    color = secondaryTextDark,
                    style = ValoStatTypography.subtitle1,
                )
            }
        }

        val abilityModifier = Modifier
            .aspectRatio(1f)
            .padding(horizontal = Padding.Dp8, vertical = Padding.Dp8)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)
                .background(abilitiesBackgroundDark),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            AsyncImage(
                modifier = abilityModifier,
                model = agent.abilities[0].displayIcon,
                imageLoader = ImageLoader(LocalContext.current),
                contentScale = ContentScale.Fit,
                contentDescription = stringResource(id = R.string.agents)
            )
            AsyncImage(
                modifier = abilityModifier,
                model = agent.abilities[1].displayIcon,
                imageLoader = ImageLoader(LocalContext.current),
                contentScale = ContentScale.Fit,
                contentDescription = stringResource(id = R.string.agents)
            )
            AsyncImage(
                modifier = abilityModifier,
                model = agent.abilities[2].displayIcon,
                imageLoader = ImageLoader(LocalContext.current),
                contentScale = ContentScale.Fit,
                contentDescription = stringResource(id = R.string.agents)
            )
            AsyncImage(
                modifier = abilityModifier,
                model = agent.abilities[3].displayIcon,
                imageLoader = ImageLoader(LocalContext.current),
                contentScale = ContentScale.Fit,
                contentDescription = stringResource(id = R.string.agents)
            )
        }
    }
}

fun agentMock(): AgentDto =
    AgentDto(
        uuid = "7f94d92c-4234-0a36-9646-3a87eb8b5c89",
        displayName = "Yoru",
        description = "Japanese native Yoru rips holes straight through reality to infiltrate enemy lines unseen. Using deception and aggression in equal measure, he gets the drop on each target before they know where to look.",
        background = "https://media.valorant-api.com/agents/7f94d92c-4234-0a36-9646-3a87eb8b5c89/background.png",
        assetPath = "ShooterGame/Content/Characters/Stealth/Stealth_PrimaryAsset",
        displayIcon = "https://media.valorant-api.com/agents/7f94d92c-4234-0a36-9646-3a87eb8b5c89/displayicon.png",
        displayIconSmall = "https://media.valorant-api.com/agents/7f94d92c-4234-0a36-9646-3a87eb8b5c89/displayicon.png",
        fullPortrait = "https://media.valorant-api.com/agents/7f94d92c-4234-0a36-9646-3a87eb8b5c89/fullportrait.png",
        fullPortraitV2 = "https://media.valorant-api.com/agents/7f94d92c-4234-0a36-9646-3a87eb8b5c89/fullportrait.png",
        killfeedPortrait = "https://media.valorant-api.com/agents/7f94d92c-4234-0a36-9646-3a87eb8b5c89/killfeedportrait.png",
        bustPortrait = "https://media.valorant-api.com/agents/7f94d92c-4234-0a36-9646-3a87eb8b5c89/fullportrait.png",
        developerName = "Stealth",
        isAvailableForTest = true,
        isBaseContent = false,
        isFullPortraitRightFacing = false,
        isPlayableCharacter = true,
        role = agentRoleMock(),
        voiceLine = voiceLineMock(),
        abilities = agentAbilitiesMock(),
        backgroundGradientColors = listOf(
            "66a8ffff",
            "3b37a7ff",
            "261e4fff",
            "101042ff"
        ),
        characterTags = emptyList()
    )

private fun agentRoleMock(): AgentRoleDto =
    AgentRoleDto(
        uuid = "dbe8757e-9e92-4ed4-b39f-9dfc589691d4",
        displayName = "Duelist",
        description = "Duelists are self-sufficient fraggers who their team expects, through abilities and skills, to get high frags and seek out engagements first.",
        assetPath = "ShooterGame/Content/Characters/_Core/Roles/Assault_PrimaryDataAsset",
        displayIcon = "https://media.valorant-api.com/agents/roles/dbe8757e-9e92-4ed4-b39f-9dfc589691d4/displayicon.png"
    )

private fun voiceLineMock(): AgentVoiceLineDto =
    AgentVoiceLineDto(
        minDuration = 4.105177,
        maxDuration = 4.105177,
        voiceline = AgentVoiceLineDto.VoiceLineMediaDto(
            id = 107320642,
            wave = "https://media.valorant-api.com/sounds/107320642.wav",
            wwise = "https://media.valorant-api.com/sounds/107320642.wem",
        )
    )

fun agentAbilitiesMock(): List<AgentAbilityDto> =
    listOf(
        AgentAbilityDto(
            displayName = "FAKEOUT",
            description = "EQUIP an echo that transforms into a mirror image of Yoru when activated. FIRE to instantly activate the mirror image and send it forward. ALT FIRE to place an inactive echo. USE to transform an inactive echo into a mirror image and send it forward.  Mirror images explode in a Blinding flash when destroyed by enemies.",
            displayIcon = "https://media.valorant-api.com/agents/7f94d92c-4234-0a36-9646-3a87eb8b5c89/abilities/grenade/displayicon.png",
            slot = "Grenade"
        ),
        AgentAbilityDto(
            displayName = "BLINDSIDE",
            description = "EQUIP to rip an unstable dimensional fragment from reality. FIRE to throw the fragment, activating a flash that winds up once it collides with a hard surface in world.",
            displayIcon = "https://media.valorant-api.com/agents/7f94d92c-4234-0a36-9646-3a87eb8b5c89/abilities/ability1/displayicon.png",
            slot = "Ability1",
        ),
        AgentAbilityDto(
            displayName = "GATECRASH",
            description = "EQUIP a rift tether FIRE to send the tether forward ALT FIRE to place a stationary tether ACTIVATE to teleport to the tether's location USE to trigger a fake teleport.",
            displayIcon = "https://media.valorant-api.com/agents/7f94d92c-4234-0a36-9646-3a87eb8b5c89/abilities/ability2/displayicon.png",
            slot = "Ability2",
        ),
        AgentAbilityDto(
            displayName = "DIMENSIONAL DRIFT",
            description = "EQUIP a mask that can see between dimensions. FIRE to drift into Yoru's dimension, unable to be affected or seen by enemies from the outside.",
            displayIcon = "https://media.valorant-api.com/agents/7f94d92c-4234-0a36-9646-3a87eb8b5c89/abilities/ultimate/displayicon.png",
            slot = "Ultimate",
        )
    )

fun agentOriginMock(): AgentOriginDto =
    AgentOriginDto(
        countryName = "South Korea",
        iconUrl = ""
    )