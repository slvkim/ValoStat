package com.mikyegresl.valostat.navigation

import android.os.Build
import android.os.Bundle
import android.os.Parcelable

fun <T: Parcelable?> Bundle.getParcelableCompat(key: String?, clazz: Class<T>): T? =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelable(key, clazz)
    } else {
        getParcelable<T>(key)
    }
