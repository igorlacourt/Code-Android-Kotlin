package com.arctouch.codechallenge.upcoming.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arctouch.codechallenge.AppConstants
import com.arctouch.codechallenge.domainmapper.MapperFunctions
import com.arctouch.codechallenge.network.Apifactory
import com.arctouch.codechallenge.network.NetworkCall
import com.arctouch.codechallenge.network.NetworkCallback
import com.arctouch.codechallenge.network.Resource
import com.arctouch.codechallenge.upcoming.domainobject.Movie
import com.arctouch.codechallenge.upcoming.dto.MovieResponseDTO

class UpcomingRepository {
    private val _listsOfMovies = MutableLiveData<Resource<ArrayList<Movie>>>()
    val listsOfMovies: LiveData<Resource<ArrayList<Movie>>>
    get() = _listsOfMovies

    fun fetchUpcoming(context: Context) {
        NetworkCall<MovieResponseDTO, ArrayList<Movie>>().makeCall(
                Apifactory.tmdbApi(context)?.getUpcomingMovies(AppConstants.LANGUAGE, 1),
                _listsOfMovies,
                MapperFunctions::movieResponseToListOfMovies
        )
    }

    fun fetchGenres() {

    }

}