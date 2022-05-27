package com.wonjoon.data

import com.wonjoon.data.room.chat.ChatRoom
import java.util.*

object DummyData {
    val chatRooms =  listOf(
        ChatRoom(0, "김승규", "test1", "fe5853b1-c297-46e0-950a-4896a0dcd9a2"),
        ChatRoom(1, "이용", "test2", "b0f042c7-a443-443c-9a82-e8cfe0dd7ca4"),
        ChatRoom(2, "김진수", "test3", "4fa0f2ef-9d62-46b9-8238-8a7bca11e23d"),
        ChatRoom(3, "김민재", "test4", "2bc58f1c-58d7-4339-b886-2c9520ff8ea5"),
        ChatRoom(4, "정우영", "test5", "efa79634-b407-4442-ac8f-29049fc6ab99"),
        ChatRoom(5, "황인범", "test6", "938af9a6-2203-4901-857a-73aa0077d2f5"),
        ChatRoom(6, "손흥민", "test7", "0ca9a76a-ce4c-4ec6-accb-fd41974a1869"),
        ChatRoom(7, "남태희", "test8", "f5d12785-eda9-40ef-8b55-6db65bb95770"),
        ChatRoom(8, "조규성", "test9", "3f467245-d39c-4d7f-9f30-37c947e469e9"),
        ChatRoom(9, "이재성", "test10", "7421742b-f5a8-47d6-ad0b-acb986d0eced"),
        ChatRoom(10, "황희찬", "test11", "13c8c6a7-35a6-4f91-9293-a0efa20e553f"),
        ChatRoom(11, "이동준", "test12", "dd095ee2-6a14-4b64-a98a-d1d6de0a3e11"),
        ChatRoom(12, "나상호", "test13", "e9efb679-fae8-4523-9a2f-9b7ce80a4f71"),
        ChatRoom(13, "홍철", "test14", "9a7200bf-f82c-453d-885e-7eec72cbf39d"),
        ChatRoom(14, "이동경", "test15", "8612d2e5-eec1-49fc-bd4e-567249136bbb"),
        ChatRoom(15, "황의조", "test16", "b3af2fd9-97f3-4114-9643-55f5015183e4"),
        ChatRoom(16, "송민규", "test17", "8e08fe8e-6a82-4f2e-aeb8-16e3251e6e13"),
        ChatRoom(17, "송범근", "test18", "47ae88ba-8846-4588-9a18-251525437736"),
        ChatRoom(18, "김영권", "test19", "b1564081-b883-4224-b51b-2ba0280e04b2"),
        ChatRoom(19, "백승호", "test20", "70a15b9e-449f-48db-a0eb-8ac2cb8e9d46"),
        ChatRoom(20, "조현우", "test21", "6fdd54f3-1081-41e4-ba6f-1734ef891f62"),
        ChatRoom(21, "정우영", "test22", "91ea7c09-af5a-4062-aa29-4c7ad95b384f"),
        ChatRoom(22, "김태환", "test23", "9bbed874-0766-44d5-9555-ad5dc0be7f00"),
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