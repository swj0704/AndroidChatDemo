package com.wonjoon.domain

data class ChatRoomItemModel(
    val id : Int,
    val name : String,
    val pubnubChannel : String,
    val pubnubUUID : String
)