package com.wonjoon.androidchatdemo.model

import com.google.gson.annotations.SerializedName

data class PubnubChatObject(
    @SerializedName("message")
    val message: ChatItemModel
)

data class ChatItemModel(
    @SerializedName("text")
    val text : String,
    @SerializedName("name")
    val name : String,
    @SerializedName("uuid")
    val uuid : String,
    @SerializedName("time")
    val time : Long = System.currentTimeMillis()
)