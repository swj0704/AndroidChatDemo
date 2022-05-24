package com.wonjoon.domain

interface UserRepository {
    suspend fun login(email : String, password : String) : Boolean
}