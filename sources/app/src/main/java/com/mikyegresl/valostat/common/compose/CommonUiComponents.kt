package com.mikyegresl.valostat.common.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.mikyegresl.valostat.R
import com.mikyegresl.valostat.ui.theme.secondaryTextDark

@Composable
fun ShowingLoadingState(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            color = secondaryTextDark,
        )
    }
}

@Composable
fun ShowingErrorState(
    modifier: Modifier = Modifier,
    errorMessage: String?
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        Snackbar {
            Text(
                text = errorMessage ?: stringResource(id = R.string.general_error_title)
            )
        }
    }
}

@Composable
fun TopBarBackButton(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit
) {
    Icon(
        modifier = modifier
            .rotate(210f)
            .clickable { onBackPressed() },
        painter = painterResource(id = R.drawable.ic_arrow_left),
        contentDescription = stringResource(id = R.string.navigate_back),
        tint = secondaryTextDark
    )
}

data class SpannableHtmlContentOptions(
    val trimSpaces: Boolean = false,
    val showImages: Boolean = true,
    val imagesHorizontalAlignment: Alignment = Alignment.Center
)