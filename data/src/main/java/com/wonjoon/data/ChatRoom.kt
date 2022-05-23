package com.wonjoon.data

data class ChatRoom(
    val id : Int,
    val name : String,
    val pubnubChannel : String,
    val pubnubUUID : String
)