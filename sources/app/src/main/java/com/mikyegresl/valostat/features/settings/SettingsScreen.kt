package com.mikyegresl.valostat.features.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mikyegresl.valostat.R
import com.mikyegresl.valostat.ui.dimen.Padding
import com.mikyegresl.valostat.ui.theme.ValoStatTypography
import com.mikyegresl.valostat.ui.theme.secondaryTextDark
import com.mikyegresl.valostat.ui.theme.washWhite

/* Supported languages:

*  en-US
*  ko-KR
*  ru-RU
*  zh-CN
*  fr-FR
*  es-ES
*  de-DE
*  ja-JP
*
* */

@Composable
fun SettingsScreen() {
    Scaffold(
        topBar = {
            SettingsTopBar()
        }
    ) { paddingValues ->
        SettingsScreenContent(
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun SettingsTopBar(
    modifier: Modifier = Modifier,
    title: String = stringResource(id = R.string.settings)
) {
    Text(
        modifier = modifier,
        text = title,
        textAlign = TextAlign.Center,
        style = ValoStatTypography.subtitle1
    )
}

@Composable
fun SettingsScreenContent(
    modifier: Modifier = Modifier,

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
                text = "${stringResource(id = R.string.language)}:",
                style = ValoStatTypography.caption.copy(color = secondaryTextDark)
            )
            Text(
                text = "Korean",
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