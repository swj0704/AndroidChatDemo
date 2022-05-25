package com.wonjoon.androidchatdemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wonjoon.androidchatdemo.view.adapter.ChatRoomListAdapter
import com.wonjoon.domain.usecase.GetChatRoomUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    val getChatRoomUseCase: GetChatRoomUseCase
) : ViewModel() {

    val adapter by lazy{
        ChatRoomListAdapter()
    }

    fun getChatList(){
        viewModelScope.launch {
            val list = getChatRoomUseCase()
            adapter.submitList(list)
        }
    }
}