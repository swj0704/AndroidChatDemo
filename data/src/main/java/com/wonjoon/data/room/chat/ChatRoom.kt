package com.wonjoon.data.room.chat

import androidx.room.Entity

@Entity
data class ChatRoom(
    val id : Int,
    val name : String,
    val pubnubChannel : String,
    val pubnubUUID : String
)