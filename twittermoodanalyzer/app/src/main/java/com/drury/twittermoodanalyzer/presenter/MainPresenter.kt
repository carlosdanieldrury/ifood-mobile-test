package com.drury.twittermoodanalyzer.presenter

import com.drury.twittermoodanalyzer.BaseApp
import com.drury.twittermoodanalyzer.Interfaces
import com.drury.twittermoodanalyzer.controller.AnalyzerController
import com.drury.twittermoodanalyzer.view.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainPresenter @Inject constructor(var mainActivity: MainActivity) : Interfaces.Presenter {

    lateinit var analyzerController: Interfaces.Controller

    override fun onCreate() {
        analyzerController = AnalyzerController(mainActivity.applicationContext as BaseApp)
    }

    fun getTweets(username: String) {
        // Call loading screen
        analyzerController.getTweetsByUsername(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( { tweetList ->
                    mainActivity.populateTweets(tweetList.toMutableList())
                }, { e ->
                    e.printStackTrace()
                })
    }
}