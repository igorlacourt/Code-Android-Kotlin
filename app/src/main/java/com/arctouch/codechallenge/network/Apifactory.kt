package com.arctouch.codechallenge.network
import android.content.Context
import com.arctouch.codechallenge.AppConstants
import com.arctouch.codechallenge.BuildConfig
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Apifactory {
    //TODO usar um singleton para todas as requisicoes do app
    //Creating Auth Interceptor to add api_key query in front of all the requests.
    private val authInterceptor = Interceptor { chain ->
        val newUrl = chain.request().url
            .newBuilder()
            .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    fun loggingClient(context: Context): OkHttpClient{
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .callTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(ConnectivityInterceptor(context))
            .addNetworkInterceptor(interceptor)
            .addNetworkInterceptor(authInterceptor)
            .build()
    }

    fun retrofit(context: Context): Retrofit = Retrofit.Builder()
        .baseUrl(AppConstants.TMDB_BASE_URL)
        .client(loggingClient(context))
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    private var tmdbApi: TmdbApi? = null

    fun tmdbApi(context: Context) : TmdbApi?{
        if(tmdbApi == null) {
            tmdbApi = retrofit(context).create(TmdbApi::class.java)
        }
        return tmdbApi
    }

}