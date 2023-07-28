package com.mikyegresl.valostat.utils

import android.os.Build
import android.os.Bundle
import android.os.Parcelable

@Suppress("DEPRECATION")
fun <T: Parcelable> Bundle.getParcelableCompat(key: String, clz: Class<T>): T? =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.getParcelable(key, clz)
    } else this.getParcelable(key)