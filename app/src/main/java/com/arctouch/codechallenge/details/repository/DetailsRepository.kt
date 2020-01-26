package com.arctouch.codechallenge.details.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.arctouch.codechallenge.AppConstants
import com.arctouch.codechallenge.details.domainobject.RecommendedMovie
import com.arctouch.codechallenge.domainmapper.MapperFunctions.toDetails
import com.arctouch.codechallenge.network.Apifactory
import com.arctouch.codechallenge.network.NetworkCall
import com.arctouch.codechallenge.network.Resource
import com.arctouch.codechallenge.upcoming.domainobject.Details
import com.arctouch.codechallenge.upcoming.dto.DetailsDTO
import com.arctouch.codechallenge.upcoming.dto.MovieResponseDTO
import com.arctouch.codechallenge.domainmapper.MapperFunctions.toDomainMovie
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DetailsRepository(val application: Application) {
    var movie: MutableLiveData<Resource<Details>> = MutableLiveData()
    var recommendedMovies: MutableLiveData<Resource<List<RecommendedMovie>>> = MutableLiveData()

    fun getDetails(id: Int) {
        Log.d("calltest", "getDetails called")
        NetworkCall<DetailsDTO, Details>().makeCall(
                Apifactory.tmdbApi(application)?.getDetails(id, AppConstants.VIDEOS_AND_CASTS),
                movie,
                ::toDetails
        )
    }
    fun getRecommendedMovies(id: Int) {
        var checkedId: Int
        checkedId = id
        if(checkedId == 330457) {
            checkedId = 109445
        }
        val disposable = CompositeDisposable()
        Apifactory.tmdbApi(application)?.getRecommendations(checkedId, AppConstants.LANGUAGE, 1)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : SingleObserver<MovieResponseDTO> {
                    override fun onSuccess(t: MovieResponseDTO) {
                        if(t.results.size  < 3){
                            getSimilar(checkedId)
                        } else {
                            if (t.results.size == 20) {
                                val last = t.results.size - 1
                                val beforeLast = t.results.size - 2
                                t.results.removeAt(last)
                                t.results.removeAt(beforeLast)
                            }
                            recommendedMovies.value =
                                    Resource.success(t.toDomainMovie())
                        }
                        disposable.dispose()
                    }

                    override fun onSubscribe(d: Disposable) {
                        disposable.add(d)
                    }

                    override fun onError(e: Throwable) {
                        disposable.dispose()
                    }
                })
    }

    fun getSimilar(id: Int) {
        val disposable = CompositeDisposable()
        Apifactory.tmdbApi(application)?.getSimilar(id, AppConstants.LANGUAGE, 1)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : SingleObserver<MovieResponseDTO> {
                    override fun onSuccess(t: MovieResponseDTO) {

                        if (t.results.size == 20) {
                            val last = t.results.size - 1
                            val beforeLast = t.results.size - 2
                            t.results.removeAt(last)
                            t.results.removeAt(beforeLast)
                        }
                        recommendedMovies.value =
                                Resource.success(t.toDomainMovie() as List)

                        disposable.dispose()
                    }

                    override fun onSubscribe(d: Disposable) {
                        disposable.add(d)
                    }

                    override fun onError(e: Throwable) {
                        disposable.dispose()
                    }
                })
    }

}


