package com.wonjoon.data

import com.wonjoon.data.mapper.ChatRoomMapper
import com.wonjoon.domain.ChatRepository
import com.wonjoon.domain.ChatRoomItemModel
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(val api : API) : ChatRepository {
    override suspend fun getChatRoom(): List<ChatRoomItemModel> {
        return DummyData.getChatDummy().map {
            ChatRoomMapper.dataToDomain(it)
        }
//        return api.getChatRoom().body()!!.map{
//            ChatRoomMapper.dataToDomain(it)
//        }
    }
}