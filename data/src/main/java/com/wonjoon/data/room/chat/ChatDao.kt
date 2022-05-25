package com.wonjoon.data.room.chat

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChatDao {
    @Query("SELECT * FROM ChatRoom")
    suspend fun getChatRoom() : List<ChatRoom>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChatRoom(chatRoom: ChatRoom)
}