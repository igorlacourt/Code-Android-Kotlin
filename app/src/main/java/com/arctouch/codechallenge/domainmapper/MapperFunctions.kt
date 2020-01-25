package com.arctouch.codechallenge.domainmapper

import com.arctouch.codechallenge.upcoming.domainobject.Movie
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
}
