package com.wonjoon.androidchatdemo.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wonjoon.androidchatdemo.databinding.ItemSearchChatRoomBinding
import com.wonjoon.domain.ChatRoomItemModel

interface OnClickChatSearchListener{
    fun onClick(chatRoom : ChatRoomItemModel)
}

class ChatSearchAdapter(val onClick : OnClickChatSearchListener) : ListAdapter<ChatRoomItemModel, ChatSearchAdapter.ChatSearchListViewHolder>(ChatSearchDiff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatSearchListViewHolder {
        return ChatSearchListViewHolder(ItemSearchChatRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false), onClick)
    }

    override fun onBindViewHolder(holder: ChatSearchListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ChatSearchListViewHolder(val binding : ItemSearchChatRoomBinding, val onClick: OnClickChatSearchListener) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : ChatRoomItemModel){
            binding.item = item
            binding.handler = onClick
        }
    }
}

object ChatSearchDiff : DiffUtil.ItemCallback<ChatRoomItemModel>(){
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