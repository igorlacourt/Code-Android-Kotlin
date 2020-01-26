package com.arctouch.codechallenge.upcoming.datasource

import android.content.Context
import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.arctouch.codechallenge.AppConstants
import com.arctouch.codechallenge.domainmapper.MapperFunctions
import com.arctouch.codechallenge.network.Apifactory
import com.arctouch.codechallenge.network.Error
import com.arctouch.codechallenge.upcoming.domainobject.Movie
import com.arctouch.codechallenge.upcoming.dto.MovieResponseDTO
import com.arctouch.codechallenge.util.NetworkExceptionType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDataSource(val context: Context, val errorCallback: PagingErrorCallback) : PageKeyedDataSource<Int, Movie>() {
  override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
    Apifactory.tmdbApi(context)?.getUpcomingMovies(AppConstants.LANGUAGE, params.key)?.enqueue(object : Callback<MovieResponseDTO> {
      override fun onResponse(call: Call<MovieResponseDTO>, response: Response<MovieResponseDTO>) {
        if (response.isSuccessful) {
          Log.d("viewstatelog", "response.isSuccessful, code = ${response.code()}")
          val responseItems = response.body()?.let { MapperFunctions.movieResponseToListOfMovies(it) }
          val key = if (params.key > 1) params.key - 1 else 0
          responseItems?.let {
            callback.onResult(responseItems, key)
          }
        } else {
          Log.d("viewstatelog", "response error, code = ${response.code()}")
          errorCallback.onError(Error(response.code(), response.message()))
        }
      }
      override fun onFailure(call: Call<MovieResponseDTO>, t: Throwable) {
        handleFailure(t)
      }
    })
  }
  override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
    Apifactory.tmdbApi(context)?.getUpcomingMovies(AppConstants.LANGUAGE, FIRST_PAGE)?.enqueue(object : Callback<MovieResponseDTO> {
      override fun onResponse(call: Call<MovieResponseDTO>, response: Response<MovieResponseDTO>) {
        if (response.isSuccessful) {
          val responseItems = response.body()?.let { MapperFunctions.movieResponseToListOfMovies(it) }
          responseItems?.let {
            callback.onResult(responseItems, null, FIRST_PAGE + 1)
          }
        } else{
          Log.d("viewstatelog", "response error, code = ${response.code()}")
          if(response.message().isEmpty())
          errorCallback.onError(Error(response.code(), "Conteúdo não encontrado. Por favor, reinicie o app e tente mais tarde."))
        }
      }
      override fun onFailure(call: Call<MovieResponseDTO>, t: Throwable) {
        handleFailure(t)
      }
    })
  }
  override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
    Apifactory.tmdbApi(context)?.getUpcomingMovies(AppConstants.LANGUAGE, params.key)?.enqueue(object : Callback<MovieResponseDTO> {
      override fun onResponse(call: Call<MovieResponseDTO>, response: Response<MovieResponseDTO>) {
        if (response.isSuccessful) {
          val responseItems = response.body()?.let { MapperFunctions.movieResponseToListOfMovies(it) }
          val key = params.key + 1
          responseItems?.let {
            callback.onResult(responseItems, key)
          }
        } else {
          Log.d("viewstatelog", "response error, code = ${response.code()}")
          errorCallback.onError(Error(response.code(), response.message()))
        }
      }
      override fun onFailure(call: Call<MovieResponseDTO>, t: Throwable) {
        handleFailure(t)
      }
    })
  }

  private fun handleResponse(response: Response<MovieResponseDTO>, params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

  }

  private fun handleFailure(t: Throwable) {
    val error = NetworkExceptionType.checkErrorType(t)
    Log.d("viewstatelog", "response error, code = ${error.cd}")
    errorCallback.onError(error)
  }

  companion object {
    const val PAGE_SIZE = 20
    const val FIRST_PAGE = 1
  }
}