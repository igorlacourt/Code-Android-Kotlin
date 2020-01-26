package com.arctouch.codechallenge.upcoming.repository

import android.annotation.SuppressLint
import android.app.Application
import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.network.Apifactory
import com.arctouch.codechallenge.network.Error
import com.arctouch.codechallenge.upcoming.datasource.MovieDataSource
import com.arctouch.codechallenge.upcoming.datasource.MovieDataSourceFactory
import com.arctouch.codechallenge.upcoming.domainobject.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.os.Looper
import android.util.Log
import android.widget.Toast
import io.reactivex.CompletableObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class UpcomingRepository(application: Application) {
    var moviePagedList: LiveData<PagedList<Movie>>? = null
    private var liveDataSource: LiveData<MovieDataSource>? = null

    private var _movieDataSourceError: MutableLiveData<Error>? = null
    var movieDataSourceError: LiveData<Error>? = null
        get() = _movieDataSourceError

    private var _mustInitDataSource: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    var mustInitDataSource: LiveData<Boolean>? = null
        get() = _mustInitDataSource

    init {
        initDataSource(application)
    }

    fun initDataSource(application: Application) {
        Log.d("initdsrc", "UpcomingRepository, initDataSource() called, mustInitDataSource = ${mustInitDataSource?.value}")
        val itemDataSourceFactory = MovieDataSourceFactory(application)
        liveDataSource = itemDataSourceFactory.movieLiveDataSource

        _movieDataSourceError = itemDataSourceFactory.movieDataSourceError

        val config = PagedList.Config.Builder()
                .setPageSize(MovieDataSource.PAGE_SIZE)
                .setEnablePlaceholders(false)
                .build()

        moviePagedList = LivePagedListBuilder(itemDataSourceFactory, config)
                .build()
    }
}
