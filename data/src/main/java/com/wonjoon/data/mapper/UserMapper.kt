package com.wonjoon.data.mapper

import com.wonjoon.data.room.user.User
import com.wonjoon.domain.UserItemModel

object UserMapper {
    fun dataToDomain(user : User) : UserItemModel{
        return UserItemModel(
            name = user.name,
            id = user.id,
            password = user.password,
            uuid = user.uuid
        )
    }
}