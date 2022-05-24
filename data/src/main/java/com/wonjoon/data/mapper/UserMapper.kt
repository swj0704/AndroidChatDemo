package com.wonjoon.data.mapper

import com.wonjoon.data.room.user.User
import com.wonjoon.domain.UserItemModel

object UserMapper {
    fun dataToDomain(user : User) : UserItemModel{
        return UserItemModel(
            name = user.name,
            email = user.email,
            password = user.password
        )
    }
}