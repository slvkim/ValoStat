package com.mikyegresl.valostat.provider

import com.mikyegresl.valostat.BuildConfig
import com.mikyegresl.valostat.base.config.ValoStatConfigProvider
object ValoStatConfigProviderImpl : ValoStatConfigProvider {

    override val apiUrl: String =
        BuildConfig.BASE_URL_DEV

    override val videoApiUrl: String =
        BuildConfig.VIDEO_API_URL
}