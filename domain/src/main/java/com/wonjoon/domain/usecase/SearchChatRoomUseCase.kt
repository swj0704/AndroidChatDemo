package com.wonjoon.domain.usecase

import com.wonjoon.domain.ChatRepository
import com.wonjoon.domain.ChatRoomItemModel
import com.wonjoon.domain.UseCase

class SearchChatRoomUseCase(val repository: ChatRepository) : UseCase {
    suspend operator fun invoke(text : String) : List<ChatRoomItemModel>{
        return repository.searchChatRoom(text)
    }
}