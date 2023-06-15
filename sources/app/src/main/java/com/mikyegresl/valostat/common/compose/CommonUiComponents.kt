package com.mikyegresl.valostat.common.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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