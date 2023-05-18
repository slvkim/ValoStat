package com.mikyegresl.valostat.features.agent.details

import android.util.Log
import android.widget.Space
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
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
import com.mikyegresl.valostat.base.model.agent.AgentAbilityDto
import com.mikyegresl.valostat.base.model.agent.AgentDto
import com.mikyegresl.valostat.base.model.agent.AgentOriginDto
import com.mikyegresl.valostat.common.compose.ShowLoadingState
import com.mikyegresl.valostat.features.agent.agentAbilitiesMock
import com.mikyegresl.valostat.features.agent.agentMock
import com.mikyegresl.valostat.features.agent.agentOriginMock
import com.mikyegresl.valostat.ui.dimen.ElemSize
import com.mikyegresl.valostat.ui.dimen.Padding
import com.mikyegresl.valostat.ui.theme.ValoStatTypography
import com.mikyegresl.valostat.ui.theme.backgroundDark
import com.mikyegresl.valostat.ui.theme.mainBackgroundDark
import com.mikyegresl.valostat.ui.theme.mainTextDark
import com.mikyegresl.valostat.ui.theme.secondaryBackgroundDark
import com.mikyegresl.valostat.ui.theme.secondaryTextDark
import com.mikyegresl.valostat.ui.theme.washWhite
import kotlinx.coroutines.flow.StateFlow

@Preview
@Composable
fun PreviewAgentDetailsAsDataState() {
    AgentDescriptionItem(
        modifier = Modifier,
        agentMock(),
        agentOriginMock(),
        7
    )
}

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
    state: AgentDetailsScreenState?,
    onBackPressed: () -> Unit
) {
    if (state == null) return
    Log.e("AgentsDetailsScreen", "exec: ")
    AgentDetailsAsDataState(
        modifier = Modifier,
        details = state.details,
        origin = state.origin,
        pointsForUltimate = state.pointsForUltimate
    ) {
        onBackPressed()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AgentDetailsAsDataState(
    modifier: Modifier = Modifier,
    details: AgentDto,
    origin: AgentOriginDto,
    pointsForUltimate: Int,
    onBackPressed: () -> Unit
) {
    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            mainBackgroundDark,
                            secondaryBackgroundDark
                        ),
                        start = Offset(x = 135f, y = 135f),
                        tileMode = TileMode.Mirror
                    )
                )
        ) {
            item {
                AgentDetailsTopBar(title = details.displayName) {
                    onBackPressed()
                }
            }
            item {
                AgentDescriptionItem(
                    Modifier.padding(top = Padding.Dp16),
                    details,
                    origin,
                    pointsForUltimate
                )
            }
            agentAbilities(
                modifier = Modifier,
                abilities = details.abilities
            ).invoke(this)
        }
    }
}

@Composable
fun AgentDetailsTopBar(
    modifier: Modifier = Modifier,
    title: String,
    onBackPressed: () -> Unit
) {
    Row(
        modifier = modifier
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
}

@Composable
fun AgentDescriptionItem(
    modifier: Modifier = Modifier,
    details: AgentDto,
    origin: AgentOriginDto,
    pointsForUltimate: Int
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Padding.Dp32)
    ) {
        AsyncImage(
            model = details.fullPortrait,
            contentScale = ContentScale.FillBounds,
            contentDescription = details.displayName
        )
        Spacer(Modifier.padding(vertical = Padding.Dp16))
        Row(
            modifier = Modifier.fillMaxWidth(),
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
                text = "${stringResource(id = R.string.role)}:",
                style = ValoStatTypography.caption.copy(color = secondaryTextDark)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = Modifier.size(ElemSize.Dp16),
                    model = details.role.displayIcon,
                    contentScale = ContentScale.Fit,
                    contentDescription = stringResource(id = R.string.role)
                )
                Spacer(modifier = Modifier.padding(horizontal = Padding.Dp4))

                Text(
                    text = details.role.displayName,
                    style = ValoStatTypography.caption
                )
            }
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
                text = "${stringResource(id = R.string.points_for_ulti)}:",
                style = ValoStatTypography.caption.copy(color = secondaryTextDark)
            )
            Text(
                text = pointsForUltimate.toString(),
                style = ValoStatTypography.caption
            )
        }
        Divider(
            modifier = Modifier.padding(vertical = Padding.Dp8),
            color = washWhite,
            thickness = 1.dp
        )
        Text(
            text = details.description,
            style = ValoStatTypography.caption
        )
        Divider(
            modifier = Modifier.padding(vertical = Padding.Dp8),
            color = washWhite,
            thickness = 1.dp
        )
    }
}

fun agentAbilities(
    modifier: Modifier = Modifier,
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