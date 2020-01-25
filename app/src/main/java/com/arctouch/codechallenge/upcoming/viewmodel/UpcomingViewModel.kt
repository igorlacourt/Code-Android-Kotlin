package com.arctouch.codechallenge.upcoming.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.arctouch.codechallenge.network.Resource
import com.arctouch.codechallenge.upcoming.datasource.MovieDataSource
import com.arctouch.codechallenge.upcoming.datasource.UserDataSourceFactory
import com.arctouch.codechallenge.upcoming.domainobject.Movie
import com.arctouch.codechallenge.upcoming.repository.UpcomingRepository

class UpcomingViewModel : ViewModel() {
//    val repository = UpcomingRepository()
//
//    val listOfMovies: LiveData<Resource<ArrayList<Movie>>>
//    get() = repository.listsOfMovies


    var moviePagedList: LiveData<PagedList<Movie>>
    private var liveDataSource: LiveData<MovieDataSource>

    init {
        val itemDataSourceFactory = UserDataSourceFactory()
        liveDataSource = itemDataSourceFactory.userLiveDataSource

        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(MovieDataSource.PAGE_SIZE)
                .build()

        moviePagedList = LivePagedListBuilder(itemDataSourceFactory, config)
                .build()
    }


    fun fetchUpcoming() {
//        repository.fetchUpcoming()
    }
}