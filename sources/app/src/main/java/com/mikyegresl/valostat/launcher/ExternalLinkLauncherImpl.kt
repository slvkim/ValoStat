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