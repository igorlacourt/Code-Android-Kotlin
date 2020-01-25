package com.arctouch.codechallenge.upcoming.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arctouch.codechallenge.network.Resource
import com.arctouch.codechallenge.upcoming.domainobject.Movie
import com.arctouch.codechallenge.upcoming.repository.UpcomingRepository

class UpcomingViewModel : ViewModel() {
    val repository = UpcomingRepository()

    val listOfMovies: LiveData<Resource<ArrayList<Movie>>>
    get() = repository.listsOfMovies

    fun fetchUpcoming() {
        repository.fetchUpcoming()
    }
}