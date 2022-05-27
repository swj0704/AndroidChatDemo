package com.wonjoon.androidchatdemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wonjoon.androidchatdemo.view.adapter.ChatSearchAdapter
import com.wonjoon.androidchatdemo.view.adapter.OnClickChatSearchListener
import com.wonjoon.domain.ChatRoomItemModel
import com.wonjoon.domain.usecase.InsertChatRoomUseCase
import com.wonjoon.domain.usecase.SearchChatRoomUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatSearchViewModel @Inject constructor(
    val searchChatRoomUseCase: SearchChatRoomUseCase,
    val insertChatRoomUseCase: InsertChatRoomUseCase
) : ViewModel() {

    private val _chatRoom = MutableLiveData<ChatRoomItemModel>()
    val chatRoom : LiveData<ChatRoomItemModel>
        get() = _chatRoom

    private val _isChatListEmpty = MutableLiveData<Boolean>()
    val isChatListEmpty : LiveData<Boolean>
        get() = _isChatListEmpty

    private val onClickChatRoom by lazy{
        object : OnClickChatSearchListener{
            override fun onClick(chatRoom: ChatRoomItemModel) {
                viewModelScope.launch {
                    insertChatRoomUseCase(chatRoom)
                    delay(500)
                    _chatRoom.value = chatRoom
                }
            }
        }
    }

    val adapter by lazy{
        ChatSearchAdapter(onClickChatRoom)
    }

    val text = MutableLiveData<String>("")

    fun textClear(){
        text.value = ""
    }

    fun searchChatRoom(text : String){
        viewModelScope.launch {
            val list = searchChatRoomUseCase(text)
            adapter.submitList(list)

            _isChatListEmpty.value = list.isEmpty()
        }
    }
}