package com.wonjoon.androidchatdemo.util

import com.pubnub.api.PNConfiguration
import com.pubnub.api.PubNub
import com.pubnub.api.enums.PNLogVerbosity
import com.wonjoon.androidchatdemo.model.ChatMessageData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject

object Util {
    var pubnub : PubNub? = null

    val adapterMessage: BehaviorSubject<Map<String, ChatMessageData>> = BehaviorSubject.create()
    val message: BehaviorSubject<Map<String, ChatMessageData>> = BehaviorSubject.create()

    val compositeDisposable = CompositeDisposable()

    fun initPubNubInstance(uuid : String) {
        if(getDefaultPnConfiguration(uuid) != null) {
            pubnub = PubNub(getDefaultPnConfiguration(uuid)!!)
        }
    }

    private fun getDefaultPnConfiguration(uuid : String): PNConfiguration? {
        return if (uuid.isNotEmpty()) {
            PNConfiguration(uuid = uuid).apply {
                subscribeKey = "sub-c-a7d7c506-f437-11eb-a3f0-7e76ce3f98e8"
                publishKey = "pub-c-55496c28-537d-44ed-bcad-88195fc64f90"
                logVerbosity = PNLogVerbosity.BODY
                secure = true
            }
        } else {
            null
        }
    }
}