package com.arctouch.codechallenge.details.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arctouch.codechallenge.details.domainobject.RecommendedMovie
import com.arctouch.codechallenge.details.repository.DetailsRepository
import com.arctouch.codechallenge.network.Resource
import com.arctouch.codechallenge.upcoming.domainobject.Details

class DetailsViewModel(application: Application) : AndroidViewModel(application){
    val app : Application = application
    private val repository: DetailsRepository = DetailsRepository(application)
    internal var movie: MutableLiveData<Resource<Details>>? = null
    internal var recommendedMovies: MutableLiveData<Resource<List<RecommendedMovie>>> = repository.recommendedMovies
//    var isInDatabase: LiveData<Boolean> = repository.isInDatabase

//    fun isInMyList(id: Int?): Boolean {
//        if (id == null)
//            return false
//        return true
//    }

//    fun isIndatabase(id: Int?){
//        repository?.isInDatabase(id)
//    }

    fun getDetails(id: Int) {
        Log.d("calltest", "fetchDetails called")
        movie = repository.movie
        repository.getDetails(id)
        repository.getRecommendedMovies(id)
    }

//    fun insert(myListItem: MyListItem) {
//        Log.d("log_is_inserted", "DetailsViewModel, insert() called")
//        repository.insert(myListItem)
//    }

//    fun delete(id: Int){
//        Log.d("log_is_inserted", "DetailsViewModel, delete() called")
//        repository.delete(id)
//    }

}
