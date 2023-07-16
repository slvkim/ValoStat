package com.mikyegresl.valostat.provider

import com.mikyegresl.valostat.BuildConfig
import com.mikyegresl.valostat.base.config.DevContactProvider

object DevContactProviderImpl: DevContactProvider {

    override fun getWhatsAppAppLink(): String = BuildConfig.WHATSAPP_APP_LINK

    override fun getWhatsAppWebLink(): String = BuildConfig.WHATSAPP_WEB_LINK

    override fun getTelegramLink(): String = BuildConfig.TELEGRAM_LINK

    override fun getLinkedInLink(): String = BuildConfig.LINKEDIN_LINK

    override fun getGithubLink(): String = BuildConfig.GITHUB_LINK

    override fun getOfficialPageLink(): String = BuildConfig.OFFICIAL_PAGE_LINK

    override fun getGooglePlayLink(): String = BuildConfig.GOOGLE_PLAY_LINK

}