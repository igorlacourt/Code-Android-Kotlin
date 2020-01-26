package com.arctouch.codechallenge.upcoming.datasource

import com.arctouch.codechallenge.network.Error

interface PagingErrorCallback {
    fun onError(error: Error)
}