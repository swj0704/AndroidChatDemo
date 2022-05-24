package com.wonjoon.data.room.chat

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ChatRoom::class], version = 1)
abstract class ChatDatabase : RoomDatabase(){
    abstract fun getDao() : ChatDao
}