package com.wonjoon.data

import com.wonjoon.data.room.chat.ChatRoom

object DummyData {
    val chatRooms =  listOf(
        ChatRoom(0, "테스트1", "test1", "fe5853b1-c297-46e0-950a-4896a0dcd9a2"),
        ChatRoom(1, "테스트2", "test2", "b0f042c7-a443-443c-9a82-e8cfe0dd7ca4"),
        ChatRoom(2, "테스트3", "test3", "4fa0f2ef-9d62-46b9-8238-8a7bca11e23d"),
        ChatRoom(3, "테스트4", "test4", "2bc58f1c-58d7-4339-b886-2c9520ff8ea5"),
        ChatRoom(4, "테스트5", "test5", "efa79634-b407-4442-ac8f-29049fc6ab99"),
        ChatRoom(5, "테스트6", "test6", "938af9a6-2203-4901-857a-73aa0077d2f5"),
        ChatRoom(6, "테스트7", "test7", "0ca9a76a-ce4c-4ec6-accb-fd41974a1869"),
    )

    fun getChatDummy(text : String): List<ChatRoom>{
        val returnList = mutableListOf<ChatRoom>()
        chatRooms.forEach{
            if(it.name.contains(text)){
                returnList.add(it)
            }
        }
        return returnList
    }
}