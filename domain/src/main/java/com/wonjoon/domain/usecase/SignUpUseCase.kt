package com.wonjoon.domain.usecase

import com.wonjoon.domain.UseCase
import com.wonjoon.domain.UserRepository

class SignUpUseCase(val repository:UserRepository) : UseCase {
    suspend operator fun invoke(email : String, password : String, name : String){
        return repository.signup(email, password, name)
    }
}