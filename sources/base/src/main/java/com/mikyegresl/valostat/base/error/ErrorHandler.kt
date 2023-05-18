package com.mikyegresl.valostat.base.error

interface ErrorHandler {

    @Suppress("TooGenericExceptionCaught")
    suspend fun <T> handleError(action: suspend () -> T): T =
        try {
            action()
        } catch (e: Throwable) {
            e.printStackTrace()
            throw mapError(e)
        }

    @Suppress("TooGenericExceptionCaught")
    fun <T> catchError(action: () -> T): T =
        try {
            action()
        } catch (e: Throwable) {
            throw mapError(e)
        }

    fun mapError(error: Throwable): ValoStatException
}
