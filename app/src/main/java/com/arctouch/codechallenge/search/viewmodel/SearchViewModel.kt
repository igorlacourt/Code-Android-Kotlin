package com.arctouch.codechallenge.search.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.arctouch.codechallenge.search.repository.SearchRepository
import com.arctouch.codechallenge.upcoming.dto.MovieDTO

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    //    private val query = MutableLiveData<String>()
    private val repository = SearchRepository(application)

    var searchResult: LiveData<ArrayList<MovieDTO>> = repository.searchResult as LiveData<ArrayList<MovieDTO>>

    fun searchMovie(title: String){
        repository.searchMovie(title)
    }


//    val searchResult: LiveData<List<MovieDTO>> = Transformations.switchMap(
//        query,
//        ::func
//    )

//    private fun func(title: String) = repository.searchMovie(title)

    /*
        The transformations aren't calculated unless an observer is observing
      the returned LiveData object.
        In other words, as soon as there's an observer attached to the 'searchResult'
      LiveData, the 'query.value' will call the 'onChange()' that will receive the
      'query' variable transformed by the 'func()" function.
      Reference: https://developer.android.com/reference/android/arch/lifecycle/Transformations
     */
//    fun searchMovie(title: String) = apply { query.value = title }

}