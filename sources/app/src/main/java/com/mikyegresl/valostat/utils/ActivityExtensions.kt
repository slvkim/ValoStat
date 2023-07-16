package com.mikyegresl.valostat.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build

fun Context.openWebsite(url: String) =
    this.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))

@Suppress("DEPRECATION")
fun Context.checkAppAvailable(appName: String): Boolean {
    val pm = this.packageManager
    return try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            pm.getPackageInfo(appName, PackageManager.PackageInfoFlags.of(0))
        } else {
            pm.getPackageInfo(appName, PackageManager.GET_ACTIVITIES)
        }
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}
