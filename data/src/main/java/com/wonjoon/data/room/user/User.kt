package com.wonjoon.data.room.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = false) val email : String,
    val password : String,
    val name : String
)