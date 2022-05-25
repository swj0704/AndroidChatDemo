package com.wonjoon.androidchatdemo.di

import android.content.Context
import android.content.SharedPreferences
import java.util.*
import javax.inject.Inject

class Prefs @Inject constructor(context: Context) {
    private val PUBNUB_UUID = "PUBNUB_UUID"
    private val NAME = "NAME"


    private val preferences: SharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE)

    var pubnubUuid: String
        get() = preferences.getString(PUBNUB_UUID, UUID.randomUUID().toString()).orEmpty()
        set(value) = preferences.edit().putString(PUBNUB_UUID, value).apply()

    var name: String
        get() = preferences.getString(NAME, "").orEmpty()
        set(value) = preferences.edit().putString(NAME, value).apply()
}