package com.wonjoon.domain

interface ChatRepository {
    suspend fun getChatRoom() : List<ChatRoomItemModel>
    suspend fun searchChatRoom(text : String) : List<ChatRoomItemModel>
}