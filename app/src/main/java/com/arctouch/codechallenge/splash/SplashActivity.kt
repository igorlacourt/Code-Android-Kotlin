package com.arctouch.codechallenge.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.arctouch.codechallenge.MainActivity
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.base.BaseActivity
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.network.Apifactory
import com.arctouch.codechallenge.network.NoInternet
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.splash_activity.*
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class SplashActivity : BaseActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        tv_splash_error.visibility = View.INVISIBLE
        tv_splash_title.visibility = View.VISIBLE


        Apifactory.tmdbApi(application)?.getGenresObservable()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    Cache.cacheGenres(it.genres)
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }, { error ->
                    when (error) {
                        is SocketTimeoutException -> {
                            error()
                        }
                        is UnknownHostException -> {
                            error()
                        }
                        is HttpException -> {
                            error()
                        }
                        is NoInternet -> {
                            error()
                        }
                        else -> {
                            error()
                        }
                    }
                })

    }

    fun error(){
        tv_splash_error.visibility = View.VISIBLE
        tv_splash_error.setText("Ocorreu um erro. \nCheque sua conexão e reinicie o app.")
        tv_splash_title.visibility = View.INVISIBLE
    }
}
