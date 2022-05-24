package com.wonjoon.androidchatdemo.viewmodel

import androidx.lifecycle.ViewModel
import com.wonjoon.domain.usecase.GetChatRoomUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    val getChatRoomUseCase: GetChatRoomUseCase
) : ViewModel() {

}