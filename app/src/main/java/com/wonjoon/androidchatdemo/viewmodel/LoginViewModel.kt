package com.wonjoon.androidchatdemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wonjoon.domain.UserItemModel
import com.wonjoon.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val loginUseCase: LoginUseCase
) : ViewModel(){

    private val _userItemModel = MutableLiveData<UserItemModel>()
    val userItemModel : LiveData<UserItemModel>
        get() = _userItemModel

    val id = MutableLiveData<String>("")
    val password = MutableLiveData<String>("")

    fun login(id : String, password : String){
        viewModelScope.launch {
            val user = loginUseCase(id, password)
            if(user != null) {
                _userItemModel.value = user!!
            }
        }
    }
}