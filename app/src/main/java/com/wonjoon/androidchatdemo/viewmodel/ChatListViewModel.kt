package com.wonjoon.androidchatdemo.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pubnub.api.models.consumer.PNBoundedPage
import com.pubnub.api.models.consumer.history.PNFetchMessageItem
import com.wonjoon.androidchatdemo.di.Prefs
import com.wonjoon.androidchatdemo.model.ChatMessageData
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

    val listener by lazy{
        object : OnClickChatRoomListener{
            override fun onClick(chatRoomItemModel: ChatRoomItemModel) {
                _chatRoom.value = chatRoomItemModel
            }
        }
    }

    val adapter by lazy{
        ChatRoomListAdapter(listener, prefs)
    }

    fun getChatList(){
        Util.compositeDisposable.clear()
        viewModelScope.launch {
            val list = getChatRoomUseCase()
            adapter.submitList(list)

            val channelList = ArrayList<String>()
            val timeList = ArrayList<Long>()
            val lastMessageList = ArrayList<String>()

            list.forEach{
                lastMessageList.add(it.pubnubChannel + prefs.pubnubUuid)
                if(prefs.preferences.contains(it.pubnubChannel + prefs.pubnubUuid)){
                    channelList.add(it.pubnubChannel + prefs.pubnubUuid)
                    timeList.add(prefs.preferences.getLong(it.pubnubChannel + prefs.pubnubUuid, System.currentTimeMillis() * 10000))
                }
            }

            getLastMessage(channelList, timeList, list, lastMessageList)
        }
    }

    fun getLastMessage(channelList : ArrayList<String>, time : ArrayList<Long>, chatRooms: List<ChatRoomItemModel>, lastMessageList : ArrayList<String>){
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
                        Util.pubnub!!.messageCounts(
                            channels = channelList.toList(),
                            channelsTimetoken = time.toList()
                        )
                            .async { result, status ->
                                if (status.error) {
                                    status.exception?.printStackTrace()
                                } else {
                                    if (result?.channels?.entries != null) {

                                        result.channels.entries.map { entry ->
                                            Util.badge.onNext(entry)
                                        }
                                    }
                                }
                            }
                    }
                } else {
                    System.err.println("Handling error")
                }
            }
        }
    }

}