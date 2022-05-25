package com.wonjoon.androidchatdemo.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wonjoon.androidchatdemo.databinding.ItemChatRoomBinding
import com.wonjoon.domain.ChatRoomItemModel

class ChatRoomListAdapter : ListAdapter<ChatRoomItemModel, ChatRoomListAdapter.ChatRoomListViewHolder>(ChatRoomDiff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoomListViewHolder {
        return ChatRoomListViewHolder(ItemChatRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ChatRoomListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ChatRoomListViewHolder(val binding : ItemChatRoomBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : ChatRoomItemModel){
            binding.item = item
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