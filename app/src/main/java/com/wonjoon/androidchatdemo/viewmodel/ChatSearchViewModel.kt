package com.wonjoon.androidchatdemo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wonjoon.androidchatdemo.view.adapter.ChatSearchAdapter
import com.wonjoon.domain.usecase.SearchChatRoomUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatSearchViewModel @Inject constructor(
    val searchChatRoomUseCase: SearchChatRoomUseCase
) : ViewModel() {

    val adapter by lazy{
        ChatSearchAdapter()
    }

    val text = MutableLiveData<String>("")

    fun textClear(){
        text.value = ""
    }

    fun searchChatRoom(text : String){
        viewModelScope.launch {
            val list = searchChatRoomUseCase(text)
            adapter.submitList(list)
        }
    }
}