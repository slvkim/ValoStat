package com.mikyegresl.valostat.features.news

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.mikyegresl.valostat.R
import com.mikyegresl.valostat.base.model.news.BannerContentTypeDto
import com.mikyegresl.valostat.base.model.news.BannerDimensionDto
import com.mikyegresl.valostat.base.model.news.BannerDto
import com.mikyegresl.valostat.base.model.news.ArticleDto
import com.mikyegresl.valostat.base.model.news.ArticleTypeDto
import com.mikyegresl.valostat.common.compose.ShowingErrorState
import com.mikyegresl.valostat.common.compose.ShowingLoadingState
import com.mikyegresl.valostat.ui.dimen.Padding
import com.mikyegresl.valostat.ui.theme.ValoStatTypography
import com.mikyegresl.valostat.ui.theme.mainTextDark
import com.mikyegresl.valostat.ui.theme.secondaryTextDark
import com.mikyegresl.valostat.ui.theme.tertiaryTextDark
import kotlinx.coroutines.flow.StateFlow

@Preview
@Composable
fun PreviewNewsList() {
    NewsList(
        modifier = Modifier,
        news = newsListMock(),
    ) {

    }
}

data class NewsScreenActions(
    val onArticleClick: (String) -> Unit = {}
)

@Composable
fun NewsScreen(
    screenState: StateFlow<NewsScreenState>,
    actions: NewsScreenActions
) {
    val state = screenState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier,
        topBar = {
            NewsScreenTopBar()
        }
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
                NewsScreenInDataState(
                    modifier = Modifier.padding(paddingValues),
                    state = viewState,
                    onArticleClick = actions.onArticleClick
                )
            }
        }
    }
}

@Composable
fun NewsScreenTopBar(
    modifier: Modifier = Modifier,
    pageTitle: String = stringResource(id = R.string.news)
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
fun NewsScreenInDataState(
    modifier: Modifier = Modifier,
    state: NewsScreenState.NewsDataState,
    onArticleClick: (String) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        NewsList(
            modifier = Modifier,
            news = state.newsList,
        ) {
            onArticleClick(it)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NewsList(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    news: List<ArticleDto>,
    onArticleClick: (String) -> Unit
) {
    val configuration = LocalConfiguration.current
    val widgetHeight by remember {
        mutableStateOf((configuration.screenHeightDp / 1.5).dp)
    }
    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            state = listState,
            contentPadding = PaddingValues(Padding.Dp8),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(news) { article ->
                NewsItem(
                    modifier = Modifier.fillMaxWidth(),
                    article = article
                ) {
                    onArticleClick(it)
                }
            }
        }
    }
}

@Composable
fun NewsItem(
    modifier: Modifier = Modifier,
    article: ArticleDto,
    onArticleClick: (String) -> Unit
) {
    Column(
        modifier = modifier.padding(Padding.Dp16)
    ) {
        Text(
            text = article.date,
            style = ValoStatTypography.body1.copy(
                fontSize = 14.sp,
                color = tertiaryTextDark
            )
        )
        Spacer(modifier = Modifier.padding(vertical = Padding.Dp8))
        AsyncImage(
            modifier = Modifier.fillMaxWidth(),
            model = article.banner?.url,
            contentDescription = article.title
        )
        Spacer(modifier = Modifier.padding(vertical = Padding.Dp8))
        Text(
            text = article.title,
            style = ValoStatTypography.caption.copy(color = secondaryTextDark)
        )
        Text(
            text = article.description,
            style = ValoStatTypography.body1.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.W600,
                lineHeight = 20.sp
            )
        )
        Spacer(modifier = Modifier.padding(vertical = Padding.Dp8))
        Button(
            colors = buttonColors(
                backgroundColor = secondaryTextDark
            ),
            shape = RoundedCornerShape(Padding.Dp8),
            onClick = { onArticleClick(
                when (article.type) {
                    ArticleTypeDto.NORMAL_ARTICLE -> article.url
                    ArticleTypeDto.EXTERNAL_LINK -> article.externalLink
                    else -> ""
                }
            ) }
        ) {
            Text(
                text = stringResource(id = R.string.see_article).uppercase(),
                style = ValoStatTypography.subtitle2.copy(color = mainTextDark)
            )
        }
        Divider(
            modifier = Modifier.padding(top = Padding.Dp16)
        )
    }
}

private fun newsListMock() = listOf(
    ArticleDto(
        id = "6cf34d09-43bb-5473-8049-3f7fdedaa404",
        uid = "blte8583593bee0f7ef",
        title = "Would VALORANT Pros Duel Aspas, ZmjjKK, or Derke?",
        description = "Watch VCT pros like Boaster, ZmjjKK, and Boostio choose between doing pushups or a Kpop dance, who they’d rather 1v1, and more!",
        date = "2023-07-18T14:00:00.000Z",
        url = "/news/esports/would-valorant-pros-duel-aspas-zmjjkk-or-derke/",
        externalLink = "https://youtu.be/OAophTFbphk",
        type = ArticleTypeDto.EXTERNAL_LINK,
        banner = BannerDto(
            dimension = BannerDimensionDto(
                height = 1080,
                width = 1920
            ),
            url = "https://images.contentstack.io/v3/assets/bltb6530b271fddd0b1/bltc1127f45fd9b5514/64adb609bd3ef812cb740d10/VCT23_OTS_Ep05_Thumbnail_16x9_Textless.jpg",
            contentType = BannerContentTypeDto.IMAGE
        )
    ),
    ArticleDto(
        id = "6cf34d09-43bb-5473-8049-3f7fdedaa404",
        uid = "blte8583593bee0f7ef",
        title = "VALORANT Patch Notes 7.01",
        description = "We’ve got Agent updates, Player Behavior updates, and a (big) Competitive update for you (hint: get your Premier team ready).",
        date = "2023-07-11T13:00:00.000Z",
        url = "/news/esports/would-valorant-pros-duel-aspas-zmjjkk-or-derke/",
        externalLink = "",
        type = ArticleTypeDto.NORMAL_ARTICLE,
        banner = BannerDto(
            dimension = BannerDimensionDto(
                height = 1080,
                width = 1920
            ),
            url = "https://images.contentstack.io/v3/assets/bltb6530b271fddd0b1/blt68537694ba536664/649dfad184a4c76a10d8e187/Val_Banner_EP7_ACT1_Deadlock_16x9.jpg",
            contentType = BannerContentTypeDto.IMAGE
        )
    )
)