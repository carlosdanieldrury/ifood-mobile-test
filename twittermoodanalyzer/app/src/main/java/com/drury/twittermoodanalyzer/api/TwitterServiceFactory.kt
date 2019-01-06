package com.drury.twittermoodanalyzer.api

import android.app.Application
import com.drury.twittermoodanalyzer.R
import com.drury.twittermoodanalyzer.utils.AppConstants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import se.akerfeldt.okhttp.signpost.SigningInterceptor
import java.util.concurrent.TimeUnit

open class TwitterServiceFactory(val application: Application) {

    private var baseUrl: String = AppConstants.BASE_URL_TWITTER_API

    private var builder = getRetrofitBuilder()

    private var retrofit: Retrofit? = null

    private fun buildClient(): OkHttpClient {

        val consumer = OkHttpOAuthConsumer(application.getString(R.string.twitter_api_key),
                application.getString(R.string.twitter_api_secret_key))
        consumer.setTokenWithSecret(application.getString(R.string.twitter_api_access_token),
                application.getString(R.string.twitter_api_token_secret))


        return OkHttpClient.Builder()
                .connectTimeout(AppConstants.REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(AppConstants.REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(SigningInterceptor(consumer))
                .build()

    }

    private fun getRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(buildClient())
                .baseUrl(baseUrl)

    }

    fun <S> createService(serviceClass: Class<S>): S {
        retrofit = builder.build()
        return retrofit!!.create(serviceClass)
    }
}