package com.mikyegresl.valostat.base.common.converter

interface Converter<From, out To> {

    companion object {
        const val EMPTY_STRING = ""
    }

    fun convert(from: From): To

    fun convert(from: List<From>?): List<To> = from?.map { convert(it) } ?: emptyList()
}