package com.arctouch.codechallenge.upcoming.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.arctouch.codechallenge.upcoming.domainobject.Movie

/**
 * Created by Morris on 03,June,2019
 */
class UserDataSourceFactory : DataSource.Factory<Int, Movie>() {
     val userLiveDataSource = MutableLiveData<MovieDataSource>()
    override fun create(): DataSource<Int, Movie> {
        val userDataSource = MovieDataSource()
        userLiveDataSource.postValue(userDataSource)
        return userDataSource
    }
}