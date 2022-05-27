package com.wonjoon.domain.usecase

import com.wonjoon.domain.UseCase
import com.wonjoon.domain.UserRepository

class SignUpUseCase(val repository:UserRepository) : UseCase {
    suspend operator fun invoke(id : String, password : String, name : String){
        return repository.signup(id, password, name)
    }
}