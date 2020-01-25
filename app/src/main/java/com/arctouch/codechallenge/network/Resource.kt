package com.arctouch.codechallenge.network

class Resource<R> private constructor(val status: Status, val data: R?, val error: Error?){
    enum class Status {
        SUCCESS, ERROR, LOADING
    }
    companion object {
        fun <R> success(data: R?): Resource<R> {
            return Resource(
                Status.SUCCESS,
                data,
                null
            )
        }
        fun <R> error(error: Error): Resource<R> {
            return Resource(
                Status.ERROR,
                null,
                error
            )
        }
        fun <R> loading(): Resource<R> {
            return Resource(
                Status.LOADING,
                null,
                null
            )
        }

    }
}