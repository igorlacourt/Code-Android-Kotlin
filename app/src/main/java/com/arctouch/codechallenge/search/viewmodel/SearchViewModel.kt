package com.arctouch.codechallenge.search.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.arctouch.codechallenge.search.repository.SearchRepository
import com.arctouch.codechallenge.upcoming.dto.MovieDTO

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = SearchRepository(application)

    var searchResult: LiveData<ArrayList<MovieDTO>> = repository.searchResult as LiveData<ArrayList<MovieDTO>>

    fun searchMovie(title: String){
        repository.searchMovie(title)
    }

}