package com.wonjoon.androidchatdemo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wonjoon.androidchatdemo.util.SingleLiveEvent
import com.wonjoon.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    val signUpUseCase: SignUpUseCase
) : ViewModel() {

    val email = MutableLiveData<String>("")
    val password = MutableLiveData<String>("")
    val name = MutableLiveData<String>("")

    val loginEvent = SingleLiveEvent<Unit>()

    fun signUp(email : String, password : String, name : String){
        viewModelScope.launch {
            signUpUseCase(email, password, name)
            loginEvent.call()
        }
    }
}