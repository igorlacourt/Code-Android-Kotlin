package com.arctouch.codechallenge.upcoming.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.home.HomeAdapter
import com.arctouch.codechallenge.network.Resource
import com.arctouch.codechallenge.upcoming.viewmodel.UpcomingViewModel
import kotlinx.android.synthetic.main.error_layout.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class UpcomingFragment : Fragment() {

    private lateinit var viewModel: UpcomingViewModel
    private var recyclerView: RecyclerView? = null
    private var adapter: PagedMoviesAdapter? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewModel =
                ViewModelProviders.of(this).get(UpcomingViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        setViewLoading(root)
        setUpRecyclerView(root)

        viewModel.movieDataSourceError?.observe(this, Observer { error ->
            Log.d("viewstatelog", "error: ${error.cd} - ${error.message}")
            setViewError(root, error.message)
        })

        viewModel.moviePagedList.observe(this, Observer {movies ->
            if(!movies.isNullOrEmpty()){
                Log.d("viewstatelog", "success: movies list size = ${movies.size}")
                setViewSuccess(root)
                adapter?.submitList(movies)
            }

//            when(response.status){
//                Resource.Status.LOADING -> {
//                    setViewLoading(root)
//                }
//                Resource.Status.SUCCESS -> {
//                    setViewSuccess(root)
//                    adapter?.setMovies(response.data)
//                }
//                Resource.Status.ERROR -> {
//                    setViewError(root)
//                }
//            }
        })
        return root
    }

    private fun setUpRecyclerView(root: View) {
        adapter = PagedMoviesAdapter()
        recyclerView = root.findViewById(R.id.rv_upcoming)
        recyclerView?.adapter = adapter
    }

    fun setViewSuccess(root: View) {
        Log.d("viewstatelog", "success")
        root.rv_upcoming.visibility = View.VISIBLE
        root.pb_loading_upcoming.visibility = View.INVISIBLE
        root.ly_error.visibility = View.INVISIBLE
    }
    fun setViewLoading(root: View) {
        Log.d("viewstatelog", "loading...")
        root.rv_upcoming.visibility = View.INVISIBLE
        root.pb_loading_upcoming.visibility = View.VISIBLE
        root.ly_error.visibility = View.INVISIBLE
    }
    fun setViewError(root: View, msg: String?) {
        Log.d("viewstatelog", "error")
        msg?.let { root.tv_error_message.text = it}
        root.rv_upcoming.visibility = View.INVISIBLE
        root.pb_loading_upcoming.visibility = View.INVISIBLE
        root.ly_error.visibility = View.VISIBLE
    }
}