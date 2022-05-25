package com.wonjoon.androidchatdemo.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.pubnub.api.PubNub
import com.pubnub.api.callbacks.SubscribeCallback
import com.pubnub.api.models.consumer.PNStatus
import com.pubnub.api.models.consumer.pubsub.PNMessageResult
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult
import com.pubnub.api.models.consumer.pubsub.PNSignalResult
import com.pubnub.api.models.consumer.pubsub.message_actions.PNMessageActionResult
import com.wonjoon.androidchatdemo.R
import com.wonjoon.androidchatdemo.databinding.ActivityChatBinding
import com.wonjoon.androidchatdemo.util.Util
import com.wonjoon.androidchatdemo.viewmodel.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

    val binding by lazy{
        ActivityChatBinding.inflate(layoutInflater)
    }

    val viewModel : ChatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.channel = pubnubChannel

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.test.setOnClickListener {
            viewModel.test(pubnubChannel, name, pubnubUUID)
        }

        messageListener()

        pubnubSubscribeChannel()

        binding.headerTitle.text = name

        viewModel.history(pubnubChannel)

        viewModel.size.observe(this) {
            binding.chatList.smoothScrollToPosition(it)
        }
    }

    private fun pubnubSubscribeChannel() {
        if (Util.pubnub != null) {
            Util.pubnub!!.subscribe(
                channels = listOf(pubnubChannel.toString()),
                withPresence = true
            )
        }
    }


    private fun messageListener() {
        if(Util.pubnub != null) {
            Util.pubnub!!.addListener(subscribeCallback)
        }
    }

    private fun removeMessageListener(){
        if(Util.pubnub != null) {
            Util.pubnub!!.removeListener(subscribeCallback)
        }
    }

    private val subscribeCallback = object : SubscribeCallback() {
        override fun status(pubnub: PubNub, status: PNStatus) {}
        override fun presence(pubnub: PubNub, pnPresenceEventResult: PNPresenceEventResult) {}
        override fun message(pubnub: PubNub, pnMessageResult: PNMessageResult) {
            this@ChatActivity.runOnUiThread {
                viewModel.receiveMessage(pnMessageResult)
            }
        }
        override fun signal(pubnub: PubNub, pnSignalResult: PNSignalResult) {}
        override fun messageAction(pubnub: PubNub, pnMessageActionResult: PNMessageActionResult) {}
    }

    override fun onDestroy() {
        removeMessageListener()
        super.onDestroy()
    }
}