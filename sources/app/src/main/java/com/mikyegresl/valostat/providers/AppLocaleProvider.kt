package com.mikyegresl.valostat.providers

import androidx.compose.ui.text.intl.Locale
import com.mikyegresl.valostat.base.model.ValoStatLocale

class AppLocaleProvider(
    val locales: Map<String, ValoStatLocale>
) {
    val appLocale: ValoStatLocale get() =
        locales.map { it.value.map(Locale.current.toLanguageTag()) }
            .firstOrNull() ?: ValoStatLocale.EN
}