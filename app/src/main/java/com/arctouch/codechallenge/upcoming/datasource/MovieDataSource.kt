package com.arctouch.codechallenge.upcoming.datasource

import androidx.paging.PageKeyedDataSource
import com.arctouch.codechallenge.AppConstants
import com.arctouch.codechallenge.domainmapper.MapperFunctions
import com.arctouch.codechallenge.network.Apifactory
import com.arctouch.codechallenge.upcoming.domainobject.Movie
import com.arctouch.codechallenge.upcoming.dto.MovieResponseDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
/**
 * Created by Morris on 03,June,2019
 */
class MovieDataSource : PageKeyedDataSource<Int, Movie>() {
  override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
    Apifactory.tmdbApi.getUpcomingMovies(AppConstants.LANGUAGE, params.key).enqueue(object : Callback<MovieResponseDTO> {
      override fun onResponse(call: Call<MovieResponseDTO>, response: Response<MovieResponseDTO>) {
        if (response.isSuccessful) {
          val responseItems = response.body()?.let { MapperFunctions.movieResponseToListOfMovies(it) }
          val key = if (params.key > 1) params.key - 1 else 0
          responseItems?.let {
            callback.onResult(responseItems, key)
          }
        }
      }
      override fun onFailure(call: Call<MovieResponseDTO>, t: Throwable) {
      }
    })
  }
  override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
    Apifactory.tmdbApi.getUpcomingMovies(AppConstants.LANGUAGE, FIRST_PAGE).enqueue(object : Callback<MovieResponseDTO> {
      override fun onResponse(call: Call<MovieResponseDTO>, response: Response<MovieResponseDTO>) {
        if (response.isSuccessful) {
          val responseItems = response.body()?.let { MapperFunctions.movieResponseToListOfMovies(it) }
          responseItems?.let {
            callback.onResult(responseItems, null, FIRST_PAGE + 1)
          }
        }
      }
      override fun onFailure(call: Call<MovieResponseDTO>, t: Throwable) {
      }
    })
  }
  override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
    Apifactory.tmdbApi.getUpcomingMovies(AppConstants.LANGUAGE, params.key).enqueue(object : Callback<MovieResponseDTO> {
      override fun onResponse(call: Call<MovieResponseDTO>, response: Response<MovieResponseDTO>) {
        if (response.isSuccessful) {
          val responseItems = response.body()?.let { MapperFunctions.movieResponseToListOfMovies(it) }
          val key = params.key + 1
          responseItems?.let {
            callback.onResult(responseItems, key)
          }
        }
      }
      override fun onFailure(call: Call<MovieResponseDTO>, t: Throwable) {
      }
    })
  }
  companion object {
    const val PAGE_SIZE = 20
    const val FIRST_PAGE = 1
  }
}