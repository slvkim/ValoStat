package com.mikyegresl.valostat.base.network

sealed class Response<out T> {

    class SuccessLocal<T>(val data: T?): Response<T>()
    class SuccessRemote<T>(val data: T?): Response<T>()
    class Error<T>(val throwable: Throwable?): Response<T>()
    class Loading<T>: Response<T>()
}