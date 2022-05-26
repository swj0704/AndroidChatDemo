package com.wonjoon.androidchatdemo.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wonjoon.androidchatdemo.databinding.ItemChatRoomBinding
import com.wonjoon.androidchatdemo.di.Prefs
import com.wonjoon.androidchatdemo.util.BindingAdapter
import com.wonjoon.androidchatdemo.util.Util
import com.wonjoon.domain.ChatRoomItemModel
import io.reactivex.rxjava3.disposables.Disposable
import java.util.HashMap

interface OnClickChatRoomListener{
    fun onClick(chatRoomItemModel: ChatRoomItemModel)
}

class ChatRoomListAdapter(val onClickChatRoomListener: OnClickChatRoomListener, val prefs : Prefs) : ListAdapter<ChatRoomItemModel, ChatRoomListAdapter.ChatRoomListViewHolder>(ChatRoomDiff) {

    val map = HashMap<String, Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoomListViewHolder {
        return ChatRoomListViewHolder(ItemChatRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false), onClickChatRoomListener)
    }

    override fun onBindViewHolder(holder: ChatRoomListViewHolder, position: Int) {
        val item = getItem(position)
        holder.remove()
        holder.bind(item)
        holder.subscribe(item)

        if(!map.containsKey(item.pubnubChannel)) {
            holder.subscribeCount(item)
            map[item.pubnubChannel] = position
        }
    }

    inner class ChatRoomListViewHolder(val binding : ItemChatRoomBinding, val onClickChatRoomListener: OnClickChatRoomListener) : RecyclerView.ViewHolder(binding.root){
        var messageDispose : Disposable? = null
        var adapterMessageDispose : Disposable? = null
        var badgeDispose : Disposable? = null

        fun bind(item : ChatRoomItemModel){
            binding.item = item
            binding.handler = onClickChatRoomListener
        }

        fun subscribe(item : ChatRoomItemModel){
            messageDispose = Util.message.subscribe({
                if(it.keys.contains(item.pubnubChannel + prefs.pubnubUuid)) {
                    item.count++
                    binding.tvRvMessage.text = it[item.pubnubChannel + prefs.pubnubUuid]!!.text
                    BindingAdapter.getDateTime(binding.tvRvDay, it[item.pubnubChannel + prefs.pubnubUuid]!!.timeToken / 10000)
                    if(item.count > 0){
                        binding.badge.text = item.count.toString()
                        binding.badge.visibility = View.VISIBLE
                    } else {
                        binding.badge.visibility = View.GONE
                    }
                }
            }, {
                it.printStackTrace()
            }, {

            })
            Util.compositeDisposable.add(
                messageDispose!!
            )

            adapterMessageDispose = Util.adapterMessage.subscribe({
                if(it.keys.contains(item.pubnubChannel + prefs.pubnubUuid)) {
                    binding.tvRvMessage.text = it[item.pubnubChannel + prefs.pubnubUuid]!!.text
                    BindingAdapter.getDateTime(binding.tvRvDay, it[item.pubnubChannel + prefs.pubnubUuid]!!.timeToken / 10000)
                }
            }, {
                it.printStackTrace()
            }, {

            })

            Util.compositeDisposable.add(
                adapterMessageDispose!!
            )
        }

        fun subscribeCount(item : ChatRoomItemModel) {
            badgeDispose = Util.badge.subscribe({
                if(it.key == item.pubnubChannel + prefs.pubnubUuid){
                    item.count = it.value.toInt()
                    if(item.count > 0){
                        binding.badge.text = item.count.toString()
                        binding.badge.visibility = View.VISIBLE
                    } else {
                        binding.badge.visibility = View.GONE
                    }
                }
            }, {
                it.printStackTrace()
            })

            Util.compositeDisposable.add(
                badgeDispose!!
            )
        }

        fun remove() {
            if (adapterMessageDispose != null) {
                Util.compositeDisposable.remove(
                    adapterMessageDispose!!
                )
            }
            if(messageDispose != null){
                Util.compositeDisposable.remove(
                    messageDispose!!
                )
            }

            if(badgeDispose != null){
                Util.compositeDisposable.remove(
                    badgeDispose!!
                )
            }
        }
    }
}

object ChatRoomDiff : DiffUtil.ItemCallback<ChatRoomItemModel>(){
    override fun areItemsTheSame(oldItem: ChatRoomItemModel, newItem: ChatRoomItemModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: ChatRoomItemModel,
        newItem: ChatRoomItemModel
    ): Boolean {
        return oldItem.id == newItem.id
    }

}