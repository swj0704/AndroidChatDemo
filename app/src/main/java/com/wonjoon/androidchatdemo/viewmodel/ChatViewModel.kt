package com.wonjoon.androidchatdemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pubnub.api.models.consumer.pubsub.PNMessageResult
import com.wonjoon.androidchatdemo.di.Prefs
import com.wonjoon.androidchatdemo.model.ChatItemModel
import com.wonjoon.androidchatdemo.model.PubnubChatObject
import com.wonjoon.androidchatdemo.util.Util
import com.wonjoon.androidchatdemo.util.toPubnubChatObject
import com.wonjoon.androidchatdemo.view.adapter.ChatAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(val prefs: Prefs) : ViewModel(){

    val adapter by lazy{
        ChatAdapter(prefs)
    }

    var isSend = false

    val text = MutableLiveData<String>("")

    val chatList = ArrayList<PubnubChatObject>()

    private val _size = MutableLiveData<Int>()
    val size : LiveData<Int>
        get() = _size

    fun receiveMessage(channelName: String, pnMessageResult: PNMessageResult){
        chatList.add(pnMessageResult.message.asJsonObject.toPubnubChatObject())
        adapter.submitList(chatList)
        prefs.preferences.edit().putLong(channelName, pnMessageResult.timetoken?:0L).apply()
        _size.value = chatList.size - 1
    }

    fun history(channelName : String){
        if(Util.pubnub != null && channelName != ""){
            Util.pubnub!!.history(
                channel = channelName,
                reverse = false,
                count = 100,
                includeTimetoken = true
            ).async { result, status ->
                if(!status.error){
                    result?.let {
                        val convertMessage = it.messages.filter { historyItemResult ->
                            val messageObj = historyItemResult.entry.asJsonObject
                            if (messageObj.has("message")) {
                                val msgObj = messageObj.get("message").asJsonObject
                                msgObj.has("text") && msgObj.has("name")
                            } else {
                                false
                            }
                        }.map { historyItemResult ->
                            val messageObj = historyItemResult.entry.asJsonObject
                            messageObj.toPubnubChatObject()
                        }

                        chatList.clear()
                        chatList.addAll(convertMessage)

                        prefs.preferences.edit().putLong(channelName, if(it.startTimetoken > it.endTimetoken) it.startTimetoken else it.endTimetoken).apply()

                        adapter.submitList(convertMessage)
                    }

                }
            }
        }
    }

    fun test(channelName: String, name : String, uuid : String){
        val message = PubnubChatObject(ChatItemModel(name, name, uuid))
        if (Util.pubnub != null && channelName != "") {
            Util.pubnub!!.publish(
                channel = channelName,
                message = message,
                shouldStore = true
            ).async { result, status ->
                isSend = false
            }
        }
    }

    fun publish(text : String, channelName: String){
        if(text != "" && !isSend) {
            isSend = true
            val message = PubnubChatObject(ChatItemModel(text, prefs.name, prefs.pubnubUuid))
            if (Util.pubnub != null && channelName != "") {
                Util.pubnub!!.publish(
                    channel = channelName,
                    message = message,
                    shouldStore = true
                ).async { result, status ->
                    if (!status.error) {
                        this.text.value = ""
                    }
                    isSend = false
                }
            }
        }
    }
}