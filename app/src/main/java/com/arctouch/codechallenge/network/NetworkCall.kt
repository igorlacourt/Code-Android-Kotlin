package com.arctouch.codechallenge.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class NetworkCall<T, R> {
    fun makeCall(call: Call<T>,
                 livedata: MutableLiveData<Resource<R>>,
                 map: (T) -> R
    ) {
        Log.d("calltest", "makeCall called")
        livedata.value = Resource.loading()

        call.enqueue(object : Callback<T> {

            override fun onFailure(call: Call<T>, t: Throwable) {
                livedata.value = checkErrorType(t)
                Log.d(
                        "calltest",
                        "onFailure, throwable massage = ${t.localizedMessage}"
                )
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    livedata.value = Resource.success(response.body()?.let { map(it) })
                    Log.d(
                            "calltest",
                            "onResponse successful, ${response.body()}"
                    )
                } else {
                    livedata.value = Resource.error(Error(response.code(), response.message()))
                    Log.d(
                            "calltest",
                            "onResponse NOT successful, response.code() = ${response.code()}"
                    )
                }
            }

        })
    }

    private fun checkErrorType(t: Throwable) : Resource<R> {
        return when (t) {
            is SocketTimeoutException -> {
                Resource.error(Error(408, t.message))
            }
            is UnknownHostException -> {
                Resource.error(Error(99, t.message))
            }
            is HttpException -> {
                Resource.error(Error(400, t.message))
            }
            else -> {
                t.printStackTrace()
                Resource.error(Error(99, t.message))
            }
        }

    }

//    fun cancel() {
//        if (::call.isInitialized) {
//            call.cancel()
//        }
//    }
}