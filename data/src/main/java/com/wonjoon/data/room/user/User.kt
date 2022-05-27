package com.wonjoon.data.room.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    val id : String,
    val password : String,
    val name : String,
    @PrimaryKey(autoGenerate = false) val uuid : String
)