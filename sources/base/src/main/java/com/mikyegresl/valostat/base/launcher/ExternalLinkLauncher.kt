package com.mikyegresl.valostat.base.launcher

import android.content.Context

interface ExternalLinkLauncher {
    fun openWhatsApp(context: Context)
    fun openTelegram(context: Context)
    fun openGithub(context: Context)
    fun openLinkedIn(context: Context)
    fun openOfficialPage(context: Context)
    fun openRateAppPage(context: Context)
}