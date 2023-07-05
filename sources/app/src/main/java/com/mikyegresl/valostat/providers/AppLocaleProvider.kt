package com.mikyegresl.valostat.providers

import android.content.res.Resources
import androidx.compose.ui.text.intl.Locale
import com.mikyegresl.valostat.R
import com.mikyegresl.valostat.base.model.ValoStatLocale

class AppLocaleProvider(
    res: Resources
) {
    val locales: Map<String, ValoStatLocale> = mapOf(
        R.string.en to ValoStatLocale.EN,
        R.string.ru to ValoStatLocale.RU,
        R.string.kr to ValoStatLocale.KR
    ).mapKeys { res.getString(it.key) }

    val appLocale: ValoStatLocale get() =
        locales.map { it.value.map(Locale.current.toLanguageTag()) }
            .firstOrNull() ?: ValoStatLocale.EN
}