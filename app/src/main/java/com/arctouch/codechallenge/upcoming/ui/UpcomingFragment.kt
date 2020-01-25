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
import com.arctouch.codechallenge.upcoming.viewmodel.UpcomingViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class UpcomingFragment : Fragment() {

    private lateinit var upcomingViewModel: UpcomingViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        upcomingViewModel =
                ViewModelProviders.of(this).get(UpcomingViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        upcomingViewModel.listOfMovies.observe(this, Observer {
            rv_upcoming.adapter = it.data?.let { movies -> HomeAdapter(movies) }
        })
        return root
    }
}