package com.mikyegresl.valostat.provider

import com.mikyegresl.valostat.BuildConfig
import com.mikyegresl.valostat.base.config.DevContactProvider

object DevContactProviderImpl: DevContactProvider {

    override fun getOfficialPageLink(): String = BuildConfig.OFFICIAL_PAGE_LINK

    override fun getGooglePlayLink(): String = BuildConfig.GOOGLE_PLAY_LINK

}