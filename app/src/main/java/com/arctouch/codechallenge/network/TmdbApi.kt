package com.arctouch.codechallenge.network

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {
    //    https://api.themoviedb.org/3/movie/top_rated?api_key=<<api_key>>&language=en-US&page=1

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("language")
        language: String,
        @Query("page")
        page: Int
    ): Call<MovieResponseDTO>

    @GET("search/movie")
    fun searchMovie(
        @Query("language")
        language: String,
        @Query("query")
        query: String,
        @Query("include_adult")
        adult: Boolean

    ): Call<MovieResponseDTO>

    //    https://api.themoviedb.org/3/movie/287947/recommendations?api_key=fef98cf6bd829f53836bb7d92b02d6ef&language=en-US&page=1
    @GET("movie/{id}/recommendations")
    fun getRecommendations(
        @Path("id")
        id: Int,
        @Query("language")
        language: String,
        @Query("page")
        page: Int
    ): Single<MovieResponseDTO>

    @GET("movie/{id}/similar")
    fun getSimilar(
        @Path("id")
        id: Int,
        @Query("language")
        language: String,
        @Query("page")
        page: Int
    ): Single<MovieResponseDTO>

    @GET("movie/{id}")//@GET("movie/{id}")
    fun getDetails(
        @Path("id")
        id: Int,
        @Query("append_to_response")
        append: String
    ): Call<DetailsDTO>

    @GET("trending/movie/day")
    fun getTrendingMovies(
        @Query("language")
        language: String,
        @Query("page")
        page: Int
    ): Observable<MovieResponseDTO>

    @GET("genre/movie/list")
    fun getGenresObservable(): Observable<GenreResponseDTO>
}