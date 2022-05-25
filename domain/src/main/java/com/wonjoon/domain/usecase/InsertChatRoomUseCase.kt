package com.wonjoon.domain.usecase

import com.wonjoon.domain.ChatRepository
import com.wonjoon.domain.ChatRoomItemModel
import com.wonjoon.domain.UseCase

class InsertChatRoomUseCase(val repository: ChatRepository) : UseCase {
    suspend operator fun invoke(chatRoomItemModel: ChatRoomItemModel){
        return repository.insertChatRoom(chatRoomItemModel)
    }
}