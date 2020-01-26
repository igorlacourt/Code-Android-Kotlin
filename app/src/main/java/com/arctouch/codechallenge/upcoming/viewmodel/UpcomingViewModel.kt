package com.arctouch.codechallenge.upcoming.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.home.HomeActivity
import com.arctouch.codechallenge.network.Apifactory
import com.arctouch.codechallenge.network.Error
import com.arctouch.codechallenge.upcoming.datasource.MovieDataSource
import com.arctouch.codechallenge.upcoming.datasource.MovieDataSourceFactory
import com.arctouch.codechallenge.upcoming.datasource.PagingErrorCallback
import com.arctouch.codechallenge.upcoming.domainobject.Movie
import com.arctouch.codechallenge.upcoming.repository.UpcomingRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UpcomingViewModel(application: Application) : AndroidViewModel(application) {
    val repository = UpcomingRepository(application)
    var moviePagedList: LiveData<PagedList<Movie>>? = repository.moviePagedList
    var movieDataSourceError: LiveData<Error>? = repository.movieDataSourceError
    var mustInitDataSource = repository.mustInitDataSource

    fun initDataSource() {
        Log.d("initdsrc", "UpcomingViewModel, mustInitDataSource = ${mustInitDataSource?.value}")
        repository.initDataSource(getApplication())
    }

}