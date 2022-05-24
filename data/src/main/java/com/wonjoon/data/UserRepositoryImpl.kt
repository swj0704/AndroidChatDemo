package com.wonjoon.data

import com.wonjoon.domain.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(val api : API) : UserRepository {
    override suspend fun login(email: String, password: String): Boolean {
        return DummyData.checkLoginDummy(email, password)
    }
}