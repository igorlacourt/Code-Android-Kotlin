package com.arctouch.codechallenge.data

import com.arctouch.codechallenge.upcoming.domainobject.Genre

object Cache {

    var genres = listOf<Genre>()

    fun cacheGenres(genres: List<Genre>) {
        this.genres = genres
    }
}
