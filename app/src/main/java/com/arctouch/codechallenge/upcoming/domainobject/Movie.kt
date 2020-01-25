package com.arctouch.codechallenge.upcoming.domainobject

data class Movie(
        val id: Int?,
        val title: String?,
        val overview: String?,
        val genres: ArrayList<String>?,
        val genreIds: ArrayList<String>?,
        val posterPath: String?,
        val backdropPath: String?,
        val releaseDate: String?
)

data class Genre(val id: Int, val name: String)