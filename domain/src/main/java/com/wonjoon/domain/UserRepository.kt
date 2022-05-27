package com.wonjoon.domain

interface UserRepository {
    suspend fun login(id : String, password : String) : UserItemModel?
    suspend fun signup(id : String, password: String, name : String)
}