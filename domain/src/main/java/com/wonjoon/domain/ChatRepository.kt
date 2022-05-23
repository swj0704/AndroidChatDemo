package com.wonjoon.domain

interface ChatRepository {
    suspend fun getChatRoom() : List<ChatRoomItemModel>
}