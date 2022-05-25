package com.wonjoon.androidchatdemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wonjoon.androidchatdemo.view.adapter.ChatRoomListAdapter
import com.wonjoon.androidchatdemo.view.adapter.OnClickChatRoomListener
import com.wonjoon.domain.ChatRoomItemModel
import com.wonjoon.domain.usecase.GetChatRoomUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    val getChatRoomUseCase: GetChatRoomUseCase
) : ViewModel() {

    private val _chatRoom = MutableLiveData<ChatRoomItemModel>()
    val chatRoom : LiveData<ChatRoomItemModel>
        get() = _chatRoom

    val listener by lazy{
        object : OnClickChatRoomListener{
            override fun onClick(chatRoomItemModel: ChatRoomItemModel) {
                _chatRoom.value = chatRoomItemModel
            }
        }
    }

    val adapter by lazy{
        ChatRoomListAdapter(listener)
    }

    fun getChatList(){
        viewModelScope.launch {
            val list = getChatRoomUseCase()
            adapter.submitList(list)
        }
    }
}