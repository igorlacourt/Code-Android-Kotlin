package com.arctouch.codechallenge.api

import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import com.arctouch.codechallenge.upcoming.domainobject.Movie
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    companion object {
        const val URL = "https://api.themoviedb.org/3/"
        const val API_KEY = ""
        const val DEFAULT_LANGUAGE = "pt-BR"
        const val DEFAULT_REGION = "BR"
    }

    @GET("genre/movie/list")
    fun genres(
            @Query("api_key") apiKey: String,
            @Query("language") language: String
    ): Observable<GenreResponse>

    @GET("movie/upcoming")
    fun upcomingMovies(
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("page") page: Long,
            @Query("region") region: String
    ): Observable<UpcomingMoviesResponse>

    @GET("movie/{id}")
    fun movie(
            @Path("id") id: Long,
            @Query("api_key") apiKey: String,
            @Query("language") language: String
    ): Observable<Movie>
}
