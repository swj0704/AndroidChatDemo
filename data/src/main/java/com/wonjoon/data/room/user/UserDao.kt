package com.wonjoon.data.room.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM User Where id = :id AND password = :password")
    suspend fun login(id : String, password : String) : List<User>

    @Insert
    suspend fun signup(user : User)
}