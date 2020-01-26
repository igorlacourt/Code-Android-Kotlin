package com.arctouch.codechallenge.search.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.arctouch.codechallenge.AppConstants
import com.arctouch.codechallenge.domainmapper.MapperFunctions
import com.arctouch.codechallenge.network.Apifactory
import com.arctouch.codechallenge.network.NetworkCall
import com.arctouch.codechallenge.upcoming.domainobject.Movie
import com.arctouch.codechallenge.upcoming.dto.MovieDTO
import com.arctouch.codechallenge.upcoming.dto.MovieResponseDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchRepository(val application: Application){
    var searchResult: MutableLiveData<ArrayList<MovieDTO>>? = MutableLiveData()

    fun searchMovie(title:String) {
        Apifactory.tmdbApi(application)?.searchMovie(AppConstants.LANGUAGE, title, false)?.enqueue(object : Callback<MovieResponseDTO> {
            override fun onFailure(call: Call<MovieResponseDTO>, t: Throwable) {

            }
            override fun onResponse(call: Call<MovieResponseDTO>, responseDTO: Response<MovieResponseDTO>) {
                if(responseDTO.isSuccessful)
                    Log.d("searchlog", "onSuccessful, result = ${responseDTO.body()}")
                    searchResult?.value = responseDTO.body()?.results
            }
        })
//        NetworkCall<MovieResponseDTO, ArrayList<Movie>>().makeCall(
//                Apifactory.tmdbApi(application)?.getUpcomingMovies(AppConstants.LANGUAGE, 1),
//                searchResult,
//                MapperFunctions::movieResponseToListOfMovies
//        )
    }


}