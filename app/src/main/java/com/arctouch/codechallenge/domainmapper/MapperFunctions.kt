package com.arctouch.codechallenge.domainmapper

import android.telecom.Call

fun Call.Details.toMyListItem(): MyListItem? {
    return kotlin.with(this) {
        MyListItem(
                id,
                poster_path,
                release_date,
                vote_average
        )
    }

}

fun List<MyListItem>.toDomainMovie(): List<DomainMovie>? {
    return this.map { myListItem ->
        DomainMovie(
                myListItem.id,
                myListItem.poster_path
        )
    }

}

fun MovieResponseDTO.toDomainMovie(): Collection<DomainMovie> {
    return this.results.map { movieDTO ->
        DomainMovie(
                movieDTO.id,
                movieDTO.poster_path
        )
    }
}
