package com.arctouch.codechallenge.network

interface NetworkCallback<T>{
    fun networkCallResult(callback: Resource<T>)
}