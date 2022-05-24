package com.wonjoon.data.room.chat

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChatRoom(
    @PrimaryKey(autoGenerate = false) val id : Int,
    val name : String,
    val pubnubChannel : String,
    val pubnubUUID : String
)