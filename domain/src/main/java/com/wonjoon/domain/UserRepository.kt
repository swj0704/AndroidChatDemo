package com.wonjoon.domain

interface UserRepository {
    suspend fun login(email : String, password : String) : UserItemModel?
    suspend fun signup(email : String, password: String, name : String)
}