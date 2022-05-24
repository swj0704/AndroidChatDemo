package com.wonjoon.data.mapper

import com.wonjoon.data.room.chat.ChatRoom
import com.wonjoon.domain.ChatRoomItemModel

object ChatRoomMapper {
    fun dataToDomain(chatRoom: ChatRoom) : ChatRoomItemModel {
        return ChatRoomItemModel(
            id = chatRoom.id,
            pubnubChannel = chatRoom.pubnubChannel,
            pubnubUUID = chatRoom.pubnubUUID,
            name = chatRoom.name
        )
    }
}