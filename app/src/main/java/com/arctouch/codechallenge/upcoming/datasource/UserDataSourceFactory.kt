package com.arctouch.codechallenge.upcoming.datasource

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.arctouch.codechallenge.network.Error
import com.arctouch.codechallenge.upcoming.domainobject.Movie

/**
 * Created by Morris on 03,June,2019
 */

class UserDataSourceFactory(val context: Context) : DataSource.Factory<Int, Movie>(), PagingErrorCallback {
    val movieLiveDataSource = MutableLiveData<MovieDataSource>()
    val movieDataSourceError = MutableLiveData<Error>()

    override fun create(): DataSource<Int, Movie> {
        val userDataSource = MovieDataSource(context,this)
        movieLiveDataSource.postValue(userDataSource)
        return userDataSource
    }
    override fun onError(error: Error) {
        movieDataSourceError.postValue(error)
    }
}