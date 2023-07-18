package com.mikyegresl.valostat.features.news.details

import android.util.Log
import android.widget.Space
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.mikyegresl.valostat.R
import com.mikyegresl.valostat.base.model.news.ArticleCategoryDto
import com.mikyegresl.valostat.base.model.news.ArticleCategoryTypeDto
import com.mikyegresl.valostat.base.model.news.ArticleDetailsDto
import com.mikyegresl.valostat.base.model.news.ArticleTagDto
import com.mikyegresl.valostat.base.model.news.ArticleTagTypeDto
import com.mikyegresl.valostat.base.model.news.ArticleTypeDto
import com.mikyegresl.valostat.base.model.news.AuthorDto
import com.mikyegresl.valostat.base.model.news.BannerContentTypeDto
import com.mikyegresl.valostat.base.model.news.BannerDimensionDto
import com.mikyegresl.valostat.base.model.news.BannerDto
import com.mikyegresl.valostat.common.compose.ShowingErrorState
import com.mikyegresl.valostat.common.compose.ShowingLoadingState
import com.mikyegresl.valostat.common.compose.TopBarBackButton
import com.mikyegresl.valostat.features.news.NewsScreenState
import com.mikyegresl.valostat.ui.dimen.Padding
import com.mikyegresl.valostat.ui.theme.ValoStatTypography
import com.mikyegresl.valostat.ui.theme.mainTextDark
import com.mikyegresl.valostat.ui.theme.secondaryTextDark
import com.mikyegresl.valostat.ui.theme.tertiaryTextDark
import kotlinx.coroutines.flow.StateFlow

@Preview
@Composable
fun PreviewNewsList() {
    NewsDetailsScreenInDataState(
        details = detailsMock()
    ) {

    }
}

data class NewsDetailsScreenActions(
    val onBackPressed: () -> Unit = {}
)

@Composable
fun NewsDetailsScreen(
    screenState: StateFlow<NewsScreenState>,
    actions: NewsDetailsScreenActions
) {
    val state = screenState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier,
    ) { paddingValues ->
        when (val viewState = state.value) {
            is NewsScreenState.NewsLoadingState -> {
                ShowingLoadingState()
            }
            is NewsScreenState.NewsErrorState -> {
                Log.e("NewsScreen", ": ", viewState.t)
                ShowingErrorState(errorMessage = viewState.t.message)
            }
            is NewsScreenState.NewsDataState -> {
//                NewsDetailsScreenInDataState(
//                    modifier = Modifier.padding(paddingValues),
//                    details = viewState,
//                    onBackPressed = actions.onBackPressed
//                )
            }
        }
    }
}

@Composable
fun NewsDetailsScreenInDataState(
    modifier: Modifier = Modifier,
    details: ArticleDetailsDto,
    onBackPressed: () -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        item {
            details.banner?.url?.let {
                ArticleBanner(
                    modifier = Modifier,
                    imageSrc = it,
                    onBackPressed = onBackPressed
                )
            }
            ArticleTitle(
                modifier = Modifier.padding(horizontal = Padding.Dp16),
                title = details.title,
                date = details.date,
                category = details.category?.title
            )
        }
    }
}

@Composable
fun ArticleBanner(
    modifier: Modifier = Modifier,
    imageSrc: String,
    onBackPressed: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Padding.Dp16, vertical = Padding.Dp16)
    ) {
        TopBarBackButton(onBackPressed = onBackPressed)
        AsyncImage(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            model = imageSrc,
            contentDescription = stringResource(id = R.string.news)
        )
    }
}

@Composable
fun ArticleTitle(
    modifier: Modifier = Modifier,
    title: String,
    date: String,
    category: String?
) {
    Column(
        modifier = modifier.padding(top = Padding.Dp16)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = date,
                style = ValoStatTypography.body1.copy(
                    fontSize = 14.sp,
                    color = tertiaryTextDark
                )
            )
            Spacer(modifier = Modifier.padding(horizontal = Padding.Dp4))
            category?.let {
                Text(
                    text = it.uppercase(),
                    color = secondaryTextDark,
                    style = ValoStatTypography.body1.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W600,
                        lineHeight = 20.sp
                    )
                )
            }
        }
        Spacer(modifier = Modifier.padding(top = Padding.Dp16))
        Text(
            text = title,
            style = ValoStatTypography.h6
        )
    }
}

@Composable
fun ArticleContent(
    modifier: Modifier = Modifier,
    content: String
) {

}

private fun detailsMock(): ArticleDetailsDto =
    ArticleDetailsDto(
        id = "e9a4a7e3-b11c-5f34-8b42-9ddedb7025d9",
        uid = "blta2c9c5c82c53e8f1",
        title = "VALORANT Patch Notes 7.01",
        description = "We’ve got Agent updates, Player Behavior updates, and a (big) Competitive update for you (hint: get your Premier team ready).",
        htmlContent = "\"<p>Hi, everyone! It’s Jo-Ellen here.</p>\\n<p>Mastered Deadlock’s Ultimate yet? Or gotten a 4k with your Neo Frontier Sheriff? Either way, we always welcome feedback so please let us know how everything’s working.</p>\\n<p>Also, Premier’s Ignition Stage is open so grab some friends and compete!</p>\\n<h2>AGENT UPDATES</h2>\\n<ul>\\n\\t<li>We've updated the ability action icons to be more consistent across all Agents and abilities that have a common cast paradigm or output. We've also added new ones to where it was necessary. These icons appear above your equipped ability.</li>\\n\\t<li>We’ve added voiceover line interactions between Gekko and Deadlock.</li>\\n</ul>\\n<h2>COMPETITIVE UPDATES</h2>\\n<h3>PREMIER</h3>\\n<p>Ignition Stage is live!</p>\\n<ul>\\n\\t<li>If you played in the Premier Open Beta, first of all, thank you! Second, everyone is starting from scratch with Ignition so you’ll have to make or join a new team this time around, too. Your team and match history will carry over from Ignition to the launch in August though, so choose your team name carefully. (You can’t change it later!)</li>\\n\\t<li>Enrollment runs until July 20, so get your team together and make sure to choose a Zone before then. The exact time for the end of Enrollment varies by Zone—make sure to check the schedule in the client for more information so you don’t miss it.</li>\\n\\t<li>Matches start on July 20 and run through August 12, with Playoffs on August 13. Earn a Premier Score of at least 375 by then to qualify and to have a chance to be crowned one of the best teams in your Division. (Oh…and get a sweet Premier Champion title and gun buddy, too.)</li>\\n</ul>\\n<p>Check out our <a href=\\\"https://playvalorant.com/en-us/news/game-updates/premier-ignition-stage-faq/\\\" target=\\\"_blank\\\">FAQ</a> for all the nitty gritty details.</p>\\n<h2>PLAYER BEHAVIOR UPDATES</h2>\\n<ul>\\n\\t<li>We added in-game bans for repeated AFK, Friendly Fire, and Queue Dodge in Competitive and Unrated modes.</li>\\n\\t<li>People who are text-muted in real time will now also be voice-muted for the entire match.</li>\\n</ul>\"",
        date = "11/07/23",
        url = "/news/game-updates/valorant-patch-notes-7-01/",
        externalLink = "",
        type = ArticleTypeDto.NORMAL_ARTICLE,
        banner = BannerDto(
            dimension = BannerDimensionDto(
                height = 1080,
                width = 1920
            ),
            url = "https://images.contentstack.io/v3/assets/bltb6530b271fddd0b1/blt68537694ba536664/649dfad184a4c76a10d8e187/Val_Banner_EP7_ACT1_Deadlock_16x9.jpg",
            contentType = BannerContentTypeDto.IMAGE,
        ),
        author = AuthorDto(
            title = "Jo-Ellen “Riot JoEllenPDF” Aragon",
            description = "Finally putting her writing degree to good use. Lover of cheeseburgers. She/her/hers.",
            role = "Community Manager, VALORANT",
            imgUrl = "https://images.contentstack.io/v3/assets/bltb6530b271fddd0b1/blted61b81718b16f75/5e7bf9bfa65b970bc593dab1/VALORANT_Logo_V.jpg",
        ),
        tags = listOf(
            ArticleTagDto(
                title = "Patch Notes",
                type = ArticleTagTypeDto.PATCH_NOTES,
                url = "/news/tags/patch-notes/",
                isHidden = true
            )
        ),
        category = ArticleCategoryDto(
            title = "Game Updates",
            type = ArticleCategoryTypeDto.GAME_UPDATES,
            url = "/news/game-updates/"
        ),
    )