package com.mikyegresl.valostat.manager

import android.os.StatFs

object StatFsProvider {

    fun createStatFs(path: String) = StatFs(path)
}
