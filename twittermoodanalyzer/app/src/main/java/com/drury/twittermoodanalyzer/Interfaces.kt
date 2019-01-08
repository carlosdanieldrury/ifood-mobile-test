package com.drury.twittermoodanalyzer

import com.drury.twittermoodanalyzer.api.DocumentSentiment
import com.drury.twittermoodanalyzer.model.TweetModel
import com.twitter.sdk.android.core.models.Tweet
import io.reactivex.Observable

interface Interfaces {

    interface Presenter {
        fun onCreate()
    }

    interface View {
        fun loadItems(tweets: List<Tweet>)
    }

    interface Controller {
        fun getTweetsByUsername(username: String): Observable<List<TweetModel>>
        fun analyzeTweetMood(sentence: String): Observable<DocumentSentiment>
    }
}