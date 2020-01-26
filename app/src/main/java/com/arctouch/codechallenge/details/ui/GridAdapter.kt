package com.arctouch.codechallenge.details.ui

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.arctouch.codechallenge.AppConstants
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.details.domainobject.RecommendedMovie
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.list_item_recommended.view.*

class GridAdapter(
        private val context: Context?,
        private val onItemClick: ItemClick,
        private var list: ArrayList<RecommendedMovie>
) : RecyclerView.Adapter<RecommendedHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedHolder {
        val view =
                LayoutInflater.from(context).inflate(R.layout.list_item_recommended, parent, false)
        return RecommendedHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecommendedHolder, position: Int) {
        Log.d("grid-log", "onBindViewHolder() called, position = $position, list.size = ${list.size}")
        // get device dimensions
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)

        val width = displayMetrics.widthPixels

        val maxHeight= width.div(3) * 1.4

        holder.poster.minimumHeight = maxHeight.toInt()
        holder.cardView.layoutParams.height = maxHeight.toInt()

        holder.apply {
            Glide.with(itemView)
                    .load("${AppConstants.TMDB_IMAGE_BASE_URL_W185}${list[position].poster_path}")
                    .apply(RequestOptions().placeholder(R.drawable.placeholder))
                    .into(poster)
//            Picasso.get()
//                    .load("${AppConstants.TMDB_IMAGE_BASE_URL_W185}${list[position].poster_path}")
//                    .placeholder(R.drawable.placeholder)
//                    .into(poster)

            cardView.setOnClickListener {
                val id = list[position].id
                if (id != null) {
                    Log.d("clickgrid", "GridAdapter, setOnClickListener, id = $id")
                    onItemClick.onItemClick(id)
                }
                else {
                    Toast.makeText(
                            context,
                            "Sorry. Can not load this movie. :/",
                            Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

    fun setList(list: List<RecommendedMovie>) {
        Log.d("grid-log", "setList() called")
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}

class RecommendedHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var poster = itemView.iv_poster
    var cardView = itemView.movie_card_view
}