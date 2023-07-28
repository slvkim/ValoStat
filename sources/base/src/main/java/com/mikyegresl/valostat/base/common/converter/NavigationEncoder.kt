package com.mikyegresl.valostat.base.common.converter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

abstract class NavigationEncoder<T: Any>(
    private val gson: Gson
) {
    private val typeToken = object : TypeToken<T>() {}.type

    fun encode(from: T): String =
        gson.toJson(from, typeToken)

    abstract fun decode(from: String): T
}