package com.arctouch.codechallenge.util

import com.arctouch.codechallenge.network.Error
import com.arctouch.codechallenge.network.NoInternet
import com.arctouch.codechallenge.network.Resource
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object NetworkExceptionType {
    fun checkErrorType(t: Throwable) : Error{
        return when (t) {
            is IOException -> {
                Error(408, t.message)
            }
            is SocketTimeoutException -> {
                Error(408, t.message)
            }
            is UnknownHostException -> {
                Error(503, t.message)
            }
            is HttpException -> {
                Error(404, t.message)
            }
            is NoInternet -> {
                Error(0, t.message)
            }
            else -> {
                t.printStackTrace()
                Error(99, t.message)
            }
        }

    }
}