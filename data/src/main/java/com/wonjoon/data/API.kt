package com.wonjoon.data

import retrofit2.Response
import retrofit2.http.GET


interface API {
    @GET("get/chat-room")
    fun getChatRoom() : Response<List<ChatRoom>>
}