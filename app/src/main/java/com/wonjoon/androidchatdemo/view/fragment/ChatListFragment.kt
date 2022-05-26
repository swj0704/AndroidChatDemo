package com.wonjoon.androidchatdemo.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.pubnub.api.PubNub
import com.pubnub.api.callbacks.SubscribeCallback
import com.pubnub.api.models.consumer.PNStatus
import com.pubnub.api.models.consumer.pubsub.PNMessageResult
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult
import com.pubnub.api.models.consumer.pubsub.PNSignalResult
import com.pubnub.api.models.consumer.pubsub.message_actions.PNMessageActionResult
import com.wonjoon.androidchatdemo.R
import com.wonjoon.androidchatdemo.databinding.FragmentChatListBinding
import com.wonjoon.androidchatdemo.di.Prefs
import com.wonjoon.androidchatdemo.model.ChatMessageData
import com.wonjoon.androidchatdemo.util.Util
import com.wonjoon.androidchatdemo.view.activity.ChatActivity
import com.wonjoon.androidchatdemo.view.activity.LoginActivity
import com.wonjoon.androidchatdemo.viewmodel.ChatListViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class ChatListFragment : Fragment() {

    val binding : FragmentChatListBinding by lazy{
        FragmentChatListBinding.inflate(layoutInflater)
    }

    val viewModel : ChatListViewModel by viewModels()

    @Inject
    lateinit var prefs: Prefs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.search.setOnClickListener {
            findNavController().navigate(R.id.action_chat_list_to_chat_search)
        }

        viewModel.chatRoom.observe(viewLifecycleOwner){
            val intent = Intent(requireContext(), ChatActivity::class.java)
            intent.putExtra("name", it.name)
            intent.putExtra("pubnubChannel", it.pubnubChannel + prefs.pubnubUuid)
            intent.putExtra("pubnubUUID", it.pubnubUUID)
            startActivity(intent)
        }

        viewModel.subscribeList.observe(viewLifecycleOwner){
            if(Util.pubnub != null) {
                Util.pubnub!!.subscribe(
                    channels = it,
                    withPresence = true
                )
            }
        }

        binding.logout.setOnClickListener {
            prefs.name = ""
            prefs.pubnubUuid = UUID.randomUUID().toString()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finishAffinity()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getChatList()
        if(Util.pubnub != null) {
            Util.pubnub!!.addListener(subscribeCallback)
        }
    }

    override fun onDetach() {
        super.onDetach()
        Util.compositeDisposable.clear()
    }

    override fun onPause() {
        super.onPause()
        if(Util.pubnub != null) {
            Util.pubnub!!.removeListener(subscribeCallback)
        }
    }

    override fun onDestroy() {
        Util.compositeDisposable.clear()
        super.onDestroy()
    }

    private val subscribeCallback = object : SubscribeCallback() {
        override fun status(pubnub: PubNub, status: PNStatus) {}
        override fun presence(pubnub: PubNub, pnPresenceEventResult: PNPresenceEventResult) {}
        override fun message(pubnub: PubNub, pnMessageResult: PNMessageResult) {
            requireActivity().runOnUiThread {
                val message = HashMap<String, ChatMessageData>()
                message[pnMessageResult.channel] = ChatMessageData(
                    pnMessageResult.message.asJsonObject?.get("message")?.asJsonObject?.get("text")?.asString ?: "",
                    pnMessageResult.timetoken?:(System.currentTimeMillis() * 10000)
                )
                Util.message.onNext(message)
            }
        }
        override fun signal(pubnub: PubNub, pnSignalResult: PNSignalResult) {}
        override fun messageAction(pubnub: PubNub, pnMessageActionResult: PNMessageActionResult) {}
    }
}