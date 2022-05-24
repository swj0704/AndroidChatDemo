package com.wonjoon.data

import com.wonjoon.data.mapper.ChatRoomMapper
import com.wonjoon.data.room.chat.ChatDao
import com.wonjoon.domain.ChatRepository
import com.wonjoon.domain.ChatRoomItemModel
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    val dao : ChatDao
) : ChatRepository {
    override suspend fun getChatRoom(): List<ChatRoomItemModel> {
        return dao.getChatRoom().map {
            ChatRoomMapper.dataToDomain(it)
        }
    }

    override suspend fun searchChatRoom(text: String): List<ChatRoomItemModel> {
        return DummyData.getChatDummy(text).map {
            ChatRoomMapper.dataToDomain(it)
        }
    }
}