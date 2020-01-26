package com.arctouch.codechallenge.upcoming.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.arctouch.codechallenge.network.Error
import com.arctouch.codechallenge.upcoming.datasource.MovieDataSource
import com.arctouch.codechallenge.upcoming.datasource.MovieDataSourceFactory
import com.arctouch.codechallenge.upcoming.datasource.PagingErrorCallback
import com.arctouch.codechallenge.upcoming.domainobject.Movie

class UpcomingViewModel(application: Application) : AndroidViewModel(application), PagingErrorCallback {
    val pagingErrorCallback = this as PagingErrorCallback

    override fun onError(error: Error) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
//    val repository = UpcomingRepository()
//
//    val listOfMovies: LiveData<Resource<ArrayList<Movie>>>
//    get() = repository.listsOfMovies


    var moviePagedList: LiveData<PagedList<Movie>>
    private var liveDataSource: LiveData<MovieDataSource>

    private val _movieDataSourceError: MutableLiveData<Error>
    var movieDataSourceError: LiveData<Error>? = null
        get() = _movieDataSourceError

    val itemDataSourceFactory = MovieDataSourceFactory(application)

    init {
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