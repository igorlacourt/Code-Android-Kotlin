package com.arctouch.codechallenge.upcoming.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.upcoming.domainobject.Movie
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.movie_item.view.*

class PagedMoviesAdapter(val onMovieClick: MovieClick) : PagedListAdapter<Movie, PagedMoviesAdapter.MovieViewHolder>(MOVIE_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagedMoviesAdapter.MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val user = getItem(position)
        user?.let { holder.bind(it, onMovieClick) }
    }

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val movieImageUrlBuilder = MovieImageUrlBuilder()

        fun bind(movie: Movie, onMovieClick: MovieClick) {
            itemView.titleTextView.text = movie.title
            itemView.genresTextView.text = "GENERos"//movie.genres?.joinToString(separator = ", ") { it.name}
            movie.genres?.map {
                itemView.genresTextView.append("$it, ")
            }
            itemView.releaseDateTextView.text = movie.releaseDate
            itemView.ly_movie_item.setOnClickListener {
                movie.id?.let { id -> onMovieClick.onMovieClick(id) }
            }
            Glide.with(itemView)
                    .load(movie.posterPath?.let { movieImageUrlBuilder.buildPosterUrl(it) })
                    .apply(RequestOptions().placeholder(R.drawable.placeholder))
                    .into(itemView.posterImageView)
        }
    }

    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                    oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                    newItem == oldItem
        }
    }
}

interface MovieClick {
    fun onMovieClick(id: Int)
}
