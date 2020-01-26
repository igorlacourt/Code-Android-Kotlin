package com.arctouch.codechallenge.domainmapper

import com.arctouch.codechallenge.details.domainobject.RecommendedMovie
import com.arctouch.codechallenge.upcoming.domainobject.Details
import com.arctouch.codechallenge.upcoming.domainobject.Movie
import com.arctouch.codechallenge.upcoming.dto.DetailsDTO
import com.arctouch.codechallenge.upcoming.dto.MovieResponseDTO

object MapperFunctions {
    fun movieResponseToListOfMovies(input: MovieResponseDTO): ArrayList<Movie> {
        return input.results.map { movieDTO ->
            Movie(
                    movieDTO.id,
                    movieDTO.title,
                    movieDTO.overview,
                    movieDTO.genres,
                    movieDTO.genre_ids,
                    movieDTO.poster_path,
                    movieDTO.backdrop_path,
                    movieDTO.release_date
            )

        } as ArrayList<Movie>
    }

    fun toDetails(input: DetailsDTO): Details {
        return with(input) {
            Details(
                    backdrop_path,
                    genres,
                    id,
                    overview,
                    poster_path,
                    release_date,
                    runtime,
                    title,
                    vote_average,
                    videos?.results,
                    casts
            )
        }
    }
    fun MovieResponseDTO.toDomainMovie(): List<RecommendedMovie> {
        return this.results.map { movieDTO ->
            RecommendedMovie(
                    movieDTO.id,
                    movieDTO.poster_path
            )
        }
    }
}
