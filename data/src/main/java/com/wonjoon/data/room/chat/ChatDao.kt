package com.wonjoon.data.room.chat

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ChatDao {
    @Query("SELECT * FROM ChatRoom")
    suspend fun getChatRoom() : List<ChatRoom>

    @Insert
    suspend fun insertChatRoom(chatRoom: ChatRoom)
}