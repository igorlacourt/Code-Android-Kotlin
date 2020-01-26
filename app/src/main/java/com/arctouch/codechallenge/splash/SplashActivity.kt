package com.arctouch.codechallenge.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.arctouch.codechallenge.MainActivity
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.base.BaseActivity
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.network.Apifactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SplashActivity : BaseActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        Apifactory.tmdbApi(application)?.getGenresObservable()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe {
                    Cache.cacheGenres(it.genres)
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
    }
}
