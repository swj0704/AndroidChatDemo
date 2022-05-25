package com.wonjoon.androidchatdemo.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wonjoon.androidchatdemo.databinding.ItemChatLeftBinding
import com.wonjoon.androidchatdemo.databinding.ItemChatRightBinding
import com.wonjoon.androidchatdemo.di.Prefs
import com.wonjoon.androidchatdemo.model.PubnubChatObject

class ChatAdapter(val prefs: Prefs) : ListAdapter<PubnubChatObject, RecyclerView.ViewHolder>(ChatDiff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == 1){
            RightMessageViewHolder(ItemChatRightBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        } else {
            LeftMessageViewHolder(ItemChatLeftBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is LeftMessageViewHolder){
            holder.bind(getItem(position))
        } else if(holder is RightMessageViewHolder){
            holder.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(getItem(position).message.uuid == prefs.pubnubUuid){
            1
        } else {
            2
        }
    }
}

class LeftMessageViewHolder(val binding : ItemChatLeftBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(item : PubnubChatObject){
        binding.item = item.message
    }
}

class RightMessageViewHolder(val binding : ItemChatRightBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(item : PubnubChatObject){
        binding.item = item.message
    }
}

object ChatDiff : DiffUtil.ItemCallback<PubnubChatObject>(){
    override fun areItemsTheSame(oldItem: PubnubChatObject, newItem: PubnubChatObject): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: PubnubChatObject, newItem: PubnubChatObject): Boolean {
        return oldItem.message == newItem.message
    }

}