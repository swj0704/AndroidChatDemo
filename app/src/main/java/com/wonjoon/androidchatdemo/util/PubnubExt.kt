package com.wonjoon.androidchatdemo.util

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.wonjoon.androidchatdemo.model.PubnubChatObject

fun JsonObject.toPubnubChatObject(): PubnubChatObject =
    Gson().fromJson(this, PubnubChatObject::class.java)