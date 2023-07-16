package com.mikyegresl.valostat.features.settings

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.mikyegresl.valostat.R
import com.mikyegresl.valostat.base.model.ValoStatLocale
import com.mikyegresl.valostat.ui.dimen.Padding
import com.mikyegresl.valostat.ui.theme.ValoStatTypography
import com.mikyegresl.valostat.ui.theme.secondaryTextDark

/* Supported languages:
*  en-US
*  ko-KR
*  ru-RU
* */

private const val TAG = "SettingsScreen"

data class SettingsScreenActions(
    val onAppLangSwitched: (ValoStatLocale) -> Unit = {},
    val onWhatsappClick: () -> Unit = {},
    val onTelegramClick: () -> Unit = {},
    val onLinkedInClick: () -> Unit = {},
    val onGithubClick: () -> Unit = {},
    val onOfficialPageClick: () -> Unit = {},
    val onRateAppClick: () -> Unit = {}
)

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewSettingsScreen() {
    SettingsScreen(
        locales = mapOf(),
        actions = SettingsScreenActions()
    )
}

@Composable
fun SettingsScreen(
    locales: Map<String, ValoStatLocale>,
    actions: SettingsScreenActions
) {
    Scaffold(
        modifier = Modifier,
        topBar = {
            SettingsTopBar()
        }
    ) { paddingValues ->
        SettingsScreenContent(
            modifier = Modifier.padding(paddingValues),
            locales = locales,
            onAppLangSwitched = actions.onAppLangSwitched,
            onWhatsappClick = actions.onWhatsappClick,
            onTelegramClick = actions.onTelegramClick,
            onLinkedInClick = actions.onLinkedInClick,
            onSourceCodeClick = actions.onGithubClick,
            onOfficialPageClick = actions.onOfficialPageClick,
            onRateAppClick = actions.onRateAppClick
        )
    }
}

@Composable
fun SettingsTopBar(
    modifier: Modifier = Modifier,
    title: String = stringResource(id = R.string.settings)
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
            text = title,
            style = ValoStatTypography.h6
        )
    }
}

@Composable
fun SettingsScreenContent(
    modifier: Modifier = Modifier,
    locales: Map<String, ValoStatLocale>,
    onAppLangSwitched: (ValoStatLocale) -> Unit,
    onWhatsappClick: () -> Unit,
    onTelegramClick: () -> Unit,
    onLinkedInClick: () -> Unit,
    onSourceCodeClick: () -> Unit,
    onOfficialPageClick: () -> Unit,
    onRateAppClick: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(
                horizontal = Padding.Dp32
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LocaleDropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            locales = locales,
            onAppLangSwitched = onAppLangSwitched
        )
        ContactMeContent(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            onWhatsappClick = onWhatsappClick,
            onTelegramClick = onTelegramClick,
            onLinkedInClick = onLinkedInClick,
            onSourceCodeClick = onSourceCodeClick,
            onOfficialPageClick = onOfficialPageClick,
            onRateAppClick = onRateAppClick,
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LocaleDropdownMenu(
    modifier: Modifier = Modifier,
    locales: Map<String, ValoStatLocale>,
    onAppLangSwitched: (ValoStatLocale) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                textStyle = ValoStatTypography.caption,
                value = stringResource(R.string.current_language),
                onValueChange = { },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                }
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                locales.keys.forEach { locale ->
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            locales[locale]?.let(onAppLangSwitched)
                        },
                        content = {
                            Text(
                                text = locale,
                                style = ValoStatTypography.caption,
                                textAlign = TextAlign.Center
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ContactMeContent(
    modifier: Modifier = Modifier,
    onWhatsappClick: () -> Unit,
    onTelegramClick: () -> Unit,
    onLinkedInClick: () -> Unit,
    onSourceCodeClick: () -> Unit,
    onOfficialPageClick: () -> Unit,
    onRateAppClick: () -> Unit
) {
    Column(
        modifier = modifier.padding(top = Padding.Dp24)
    ) {
        Text(
            text = "${stringResource(id = R.string.useful_links)}:",
            style = ValoStatTypography.caption.copy(fontSize = 16.sp)
        )
        Spacer(
            modifier = Modifier.padding(top = Padding.Dp8)
        )
        Text(
            modifier = Modifier.clickable { onSourceCodeClick() },
            text = stringResource(id = R.string.source_code),
            style = ValoStatTypography.caption.copy(
                color = secondaryTextDark,
                fontStyle = FontStyle.Italic,
                textDecoration = TextDecoration.Underline
            )
        )
        Spacer(modifier = Modifier.padding(top = Padding.Dp8))
        Text(
            modifier = Modifier.clickable { onOfficialPageClick() },
            text = stringResource(id = R.string.official_page),
            style = ValoStatTypography.caption.copy(
                color = secondaryTextDark,
                fontStyle = FontStyle.Italic,
                textDecoration = TextDecoration.Underline
            )
        )
        Spacer(modifier = Modifier.padding(top = Padding.Dp8))
        Text(
            modifier = Modifier.clickable { onRateAppClick() },
            text = stringResource(id = R.string.rate_app),
            style = ValoStatTypography.caption.copy(
                color = secondaryTextDark,
                fontStyle = FontStyle.Italic,
                textDecoration = TextDecoration.Underline
            )
        )
    }
    Column(
        modifier = modifier.padding(top = Padding.Dp16)
    ) {
        Text(
            text = "${stringResource(id = R.string.contact_me)}:",
            style = ValoStatTypography.caption.copy(fontSize = 16.sp)
        )
        Spacer(modifier = Modifier.padding(vertical = Padding.Dp8))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                modifier = Modifier
                    .clickable { onWhatsappClick() }
                    .padding(end = Padding.Dp16),
                painter = painterResource(id = R.drawable.ic_whatsapp),
                contentDescription = stringResource(id = R.string.whatsapp)
            )
            Icon(
                modifier = Modifier
                    .clickable { onTelegramClick() }
                    .padding(end = Padding.Dp16),
                painter = painterResource(id = R.drawable.ic_telegram),
                contentDescription = stringResource(id = R.string.telegram)
            )
            Icon(
                modifier = Modifier
                    .clickable { onLinkedInClick() }
                    .padding(end = Padding.Dp16),
                painter = painterResource(id = R.drawable.ic_linkedin),
                contentDescription = stringResource(id = R.string.linkedin)
            )
        }
    }
}