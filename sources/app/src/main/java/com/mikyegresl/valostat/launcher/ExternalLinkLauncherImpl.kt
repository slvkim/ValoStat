package com.mikyegresl.valostat.launcher

import android.content.Context
import android.content.Intent
import com.mikyegresl.valostat.base.config.DevContactProvider
import com.mikyegresl.valostat.base.launcher.ExternalLinkLauncher
import com.mikyegresl.valostat.utils.checkAppAvailable
import com.mikyegresl.valostat.utils.openWebsite

class ExternalLinkLauncherImpl(
    private val contactProvider: DevContactProvider
): ExternalLinkLauncher {

    override fun openWhatsApp(context: Context) {
        launchAppOrWebpage(
            context = context,
            appName = "com.whatsapp",
            setupIntent = {
                it.type = "phone_number"
                it.putExtra(Intent.EXTRA_PHONE_NUMBER, contactProvider.getWhatsAppAppLink())
            },
            openWebpage = {
                context.openWebsite(contactProvider.getWhatsAppWebLink())
            }
        )
    }

    override fun openTelegram(context: Context) {
        launchAppOrWebpage(
            context = context,
            appName = "org.telegram.messenger",
            setupIntent = {
                it.type = "text/plain"
                it.putExtra(Intent.EXTRA_USER, contactProvider.getTelegramLink())
            },
            openWebpage = {
                context.openWebsite(contactProvider.getTelegramLink())
            }
        )
    }

    override fun openGithub(context: Context) {
        context.openWebsite(contactProvider.getGithubLink())
    }

    override fun openLinkedIn(context: Context) {
        context.openWebsite(contactProvider.getLinkedInLink())
    }

    override fun openOfficialPage(context: Context) =
        context.openWebsite(contactProvider.getOfficialPageLink())

    override fun openRateAppPage(context: Context) =
        context.openWebsite(contactProvider.getGooglePlayLink())

    private inline fun launchAppOrWebpage(
        context: Context,
        appName: String,
        setupIntent: (Intent) -> Unit,
        openWebpage: () -> Unit
    ) {
        val isAppInstalled: Boolean = context.checkAppAvailable(appName)
        if (isAppInstalled) {
            Intent(Intent.ACTION_SEND).apply {
                setPackage(appName)
                setupIntent(this)
                context.startActivity(this)
            }
        }
        else openWebpage()
    }
}