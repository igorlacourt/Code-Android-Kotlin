package com.arctouch.codechallenge.model

import com.arctouch.codechallenge.upcoming.domainobject.Genre
import com.arctouch.codechallenge.upcoming.domainobject.Movie
import com.squareup.moshi.Json

data class GenreResponse(val genres: List<Genre>)

data class UpcomingMoviesResponse(
        val page: Int,
        val results: List<Movie>,
        @Json(name = "total_pages") val totalPages: Int,
        @Json(name = "total_results") val totalResults: Int
)