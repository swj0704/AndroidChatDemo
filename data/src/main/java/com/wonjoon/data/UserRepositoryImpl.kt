package com.wonjoon.data

import com.wonjoon.data.mapper.UserMapper
import com.wonjoon.data.room.user.User
import com.wonjoon.data.room.user.UserDao
import com.wonjoon.domain.UserItemModel
import com.wonjoon.domain.UserRepository
import java.util.*
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    val dao: UserDao
) : UserRepository {
    override suspend fun login(email: String, password: String): UserItemModel? {
        return try {
            dao.login(email, password).map {
                UserMapper.dataToDomain(it)
            }[0]
        } catch (e : Exception){
            e.printStackTrace()
            null
        }
    }

    override suspend fun signup(email: String, password: String, name: String) {
         try {
             return dao.signup(User(email, password, name, UUID.randomUUID().toString()))
        } catch (e : Exception){
            e.printStackTrace()
             return
        }
    }
}