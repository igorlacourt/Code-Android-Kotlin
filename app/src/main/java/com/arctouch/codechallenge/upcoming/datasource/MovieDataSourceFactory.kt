package com.arctouch.codechallenge.upcoming.datasource

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.arctouch.codechallenge.network.Error
import com.arctouch.codechallenge.upcoming.domainobject.Movie

class MovieDataSourceFactory(val context: Context) : DataSource.Factory<Int, Movie>(), PagingErrorCallback {
    val movieLiveDataSource = MutableLiveData<MovieDataSource>()
    val movieDataSourceError = MutableLiveData<Error>()
    val movieDataSource = MovieDataSource(context,this)

    override fun create(): DataSource<Int, Movie> {
        movieLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
    override fun onError(error: Error) {
        movieDataSourceError.postValue(error)
    }

}