package com.wonjoon.data.room.user

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 2)
abstract class UserDatabase : RoomDatabase(){
    abstract fun getDao() : UserDao
}