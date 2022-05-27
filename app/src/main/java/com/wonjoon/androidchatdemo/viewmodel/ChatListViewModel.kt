package com.wonjoon.androidchatdemo.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pubnub.api.models.consumer.PNBoundedPage
import com.pubnub.api.models.consumer.history.PNFetchMessageItem
import com.wonjoon.androidchatdemo.di.Prefs
import com.wonjoon.androidchatdemo.model.ChatItemModel
import com.wonjoon.androidchatdemo.model.ChatMessageData
import com.wonjoon.androidchatdemo.model.PubnubChatObject
import com.wonjoon.androidchatdemo.util.Util
import com.wonjoon.androidchatdemo.view.adapter.ChatRoomListAdapter
import com.wonjoon.androidchatdemo.view.adapter.OnClickChatRoomListener
import com.wonjoon.domain.ChatRoomItemModel
import com.wonjoon.domain.usecase.GetChatRoomUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    val getChatRoomUseCase: GetChatRoomUseCase,
    val prefs: Prefs
) : ViewModel() {

    private val _chatRoom = MutableLiveData<ChatRoomItemModel>()
    val chatRoom : LiveData<ChatRoomItemModel>
        get() = _chatRoom

    private val _subscribeList = MutableLiveData<List<String>>()
    val subscribeList : LiveData<List<String>>
        get() = _subscribeList

    private val _isChatListEmpty = MutableLiveData<Boolean>()
    val isChatListEmpty : LiveData<Boolean>
        get() = _isChatListEmpty

    val listener by lazy{
        object : OnClickChatRoomListener{
            override fun onClick(chatRoomItemModel: ChatRoomItemModel) {
                _chatRoom.value = chatRoomItemModel
            }

            override fun onClickTestOutRoom(chatRoomItemModel: ChatRoomItemModel) {
                test(chatRoomItemModel.pubnubChannel + prefs.pubnubUuid, chatRoomItemModel.name, chatRoomItemModel.pubnubUUID)
            }
        }
    }

    val adapter by lazy{
        ChatRoomListAdapter(listener, prefs)
    }

    fun getChatList(){
        viewModelScope.launch {
            val list = getChatRoomUseCase()
            adapter.submitList(list)

            val lastMessageList = ArrayList<String>()

            _isChatListEmpty.value = list.isEmpty()

            list.forEach{
                lastMessageList.add(it.pubnubChannel + prefs.pubnubUuid)
            }

            _subscribeList.value = lastMessageList

            getLastMessage(list, lastMessageList)
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

            }
        }
    }

    fun getLastMessage(chatRooms: List<ChatRoomItemModel>, lastMessageList : ArrayList<String>){
        if(Util.pubnub != null) {
            Util.pubnub!!.fetchMessages(
                channels = lastMessageList,
                page = PNBoundedPage(limit = 1)
            ).async { result, status ->
                if (!status.error) {
                    val channelToMessageItemsMap: Map<String, List<PNFetchMessageItem>> =
                        result!!.channels
                    val channels = channelToMessageItemsMap.keys

                    if (chatRooms.isNotEmpty()) {
                        channels.map {
                            val map = HashMap<String, ChatMessageData>()
                            map[it] = ChatMessageData(
                                channelToMessageItemsMap[it]?.get(0)?.message?.asJsonObject?.get("message")?.asJsonObject?.get(
                                    "text"
                                )?.asString ?: "",
                                channelToMessageItemsMap[it]?.get(0)?.timetoken ?: 0L
                            )
                            Util.adapterMessage.onNext(map)
                        }
                    }
                } else {
                    System.err.println("Handling error")
                }
            }
        }
    }

}