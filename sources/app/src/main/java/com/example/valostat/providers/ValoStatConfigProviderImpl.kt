package com.example.valostat.providers

import com.example.valostat.BuildConfig
import com.mikyegresl.valostat.base.config.ValoStatConfigProvider

object ValoStatConfigProviderImpl : ValoStatConfigProvider {

    override val apiUrl: String =
        BuildConfig.BASE_URL_DEV
}