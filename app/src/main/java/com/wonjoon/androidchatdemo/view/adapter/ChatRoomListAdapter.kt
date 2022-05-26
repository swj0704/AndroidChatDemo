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
    fun onClickTestOutRoom(chatRoomItemModel: ChatRoomItemModel)
}

class ChatRoomListAdapter(val onClickChatRoomListener: OnClickChatRoomListener, val prefs : Prefs) : ListAdapter<ChatRoomItemModel, ChatRoomListAdapter.ChatRoomListViewHolder>(ChatRoomDiff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoomListViewHolder {
        return ChatRoomListViewHolder(ItemChatRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false), onClickChatRoomListener)
    }

    override fun onBindViewHolder(holder: ChatRoomListViewHolder, position: Int) {
        val item = getItem(position)
        holder.remove()
        holder.bind(item)
        holder.subscribe(item)
    }

    inner class ChatRoomListViewHolder(val binding : ItemChatRoomBinding, val onClickChatRoomListener: OnClickChatRoomListener) : RecyclerView.ViewHolder(binding.root){
        var messageDispose : Disposable? = null
        var adapterMessageDispose : Disposable? = null

        fun bind(item : ChatRoomItemModel){
            binding.item = item
            binding.handler = onClickChatRoomListener
        }

        fun subscribe(item : ChatRoomItemModel){
            messageDispose = Util.message.subscribe({
                if(it.keys.contains(item.pubnubChannel + prefs.pubnubUuid)) {
                    binding.tvRvMessage.text = it[item.pubnubChannel + prefs.pubnubUuid]!!.text
                    BindingAdapter.getDateTime(binding.tvRvDay, it[item.pubnubChannel + prefs.pubnubUuid]!!.timeToken / 10000)
                    binding.badge.visibility = View.VISIBLE
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
                    if(prefs.preferences.getLong(item.pubnubChannel + prefs.pubnubUuid, 0L) < it[item.pubnubChannel + prefs.pubnubUuid]!!.timeToken){
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
                adapterMessageDispose!!
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