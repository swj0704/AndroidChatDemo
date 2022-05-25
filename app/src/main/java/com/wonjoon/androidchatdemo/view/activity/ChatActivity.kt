package com.wonjoon.androidchatdemo.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wonjoon.androidchatdemo.R

class ChatActivity : AppCompatActivity() {

    val name : String by lazy{
        intent.getStringExtra("name")?:""
    }
    val pubnubChannel : String by lazy{
        intent.getStringExtra("pubnubChannel")?:""
    }
    val pubnubUUID : String by lazy{
        intent.getStringExtra("pubnubUUID")?:""
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
    }
}