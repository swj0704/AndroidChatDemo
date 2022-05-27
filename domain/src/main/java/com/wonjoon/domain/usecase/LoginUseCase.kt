package com.wonjoon.domain.usecase

import com.wonjoon.domain.UseCase
import com.wonjoon.domain.UserItemModel
import com.wonjoon.domain.UserRepository

class LoginUseCase(val repository: UserRepository) : UseCase {
    suspend operator fun invoke(id : String, password : String) : UserItemModel? {
        return repository.login(id, password)
    }
}