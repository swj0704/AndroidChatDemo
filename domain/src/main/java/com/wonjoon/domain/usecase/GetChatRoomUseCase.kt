package com.wonjoon.domain.usecase

import com.wonjoon.domain.ChatRepository
import com.wonjoon.domain.ChatRoomItemModel
import com.wonjoon.domain.UseCase

class GetChatRoomUseCase(val repository: ChatRepository) : UseCase {
    suspend operator fun invoke() : List<ChatRoomItemModel>{
        return repository.getChatRoom()
    }
}