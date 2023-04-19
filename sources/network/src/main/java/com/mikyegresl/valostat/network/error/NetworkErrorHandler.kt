package com.mikyegresl.valostat.network.error

import com.mikyegresl.valostat.base.error.ErrorHandler
import com.mikyegresl.valostat.base.error.NoInternetException
import com.mikyegresl.valostat.base.error.NotFoundException
import com.mikyegresl.valostat.base.error.UnAuthorizedAccessException
import com.mikyegresl.valostat.base.error.ValoStatException
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException

internal class NetworkErrorHandler : ErrorHandler {

    override fun mapError(error: Throwable): ValoStatException =
        when (error) {
            is ValoStatException -> error
            is HttpException -> handleHttpException(error)
            is SocketTimeoutException,
            is IOException -> NoInternetException(error)
            else -> ValoStatException(error)
        }

    private fun handleHttpException(httpException: HttpException): ValoStatException =
        when (httpException.code()) {
            HttpURLConnection.HTTP_UNAUTHORIZED,
            HttpURLConnection.HTTP_FORBIDDEN ->
                UnAuthorizedAccessException(httpException)
            HttpURLConnection.HTTP_NOT_FOUND -> NotFoundException(httpException)
            else -> ValoStatException(httpException)
        }
}
