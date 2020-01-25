package com.arctouch.codechallenge.upcoming.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.home.HomeAdapter
import com.arctouch.codechallenge.network.Resource
import com.arctouch.codechallenge.upcoming.viewmodel.UpcomingViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class UpcomingFragment : Fragment() {

    private lateinit var viewModel: UpcomingViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewModel =
                ViewModelProviders.of(this).get(UpcomingViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)


        viewModel.fetchUpcoming()
        viewModel.listOfMovies.observe(this, Observer {response ->
            when(response){
                Resource.Status.LOADING -> {
                    setViewLoading(root)
                }
                Resource.Status.SUCCESS -> {
                    setViewSuccess(root)
                    rv_upcoming.adapter(response.data)
                }
                Resource.Status.ERROR -> {
                    setViewError(root)
                }
            }
            rv_upcoming.adapter = it.data?.let { movies -> HomeAdapter(movies) }
        })
        return root
    }

    fun setViewSuccess(root: View) {
        root.rv_upcoming.visibility = View.VISIBLE
        root.pb_loading_upcoming.visibility = View.INVISIBLE
        root.ly_error.visibility = View.INVISIBLE
    }
    fun setViewLoading(root: View) {
        root.rv_upcoming.visibility = View.INVISIBLE
        root.pb_loading_upcoming.visibility = View.VISIBLE
        root.ly_error.visibility = View.INVISIBLE
    }
    fun setViewError(root: View) {
        root.rv_upcoming.visibility = View.INVISIBLE
        root.pb_loading_upcoming.visibility = View.INVISIBLE
        root.ly_error.visibility = View.VISIBLE
    }
}