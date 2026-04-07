package com.example.videoplayer_kt.presentation.channel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.videoplayer_kt.databinding.ItemChannelBinding
import com.example.videoplayer_kt.domain.models.Channel

class ChannelAdapter : ListAdapter<Channel, ChannelAdapter.ChannelViewHolder>(DiffCallback()) {

    inner class ChannelViewHolder(
        private val binding: ItemChannelBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(channel: Channel){
            binding.tvChannelName.text = channel.name
            binding.tvChannelGroup.text = channel.group ?: "Sin grupo"
            binding.ivChannelIcon.load(channel.logo)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChannelViewHolder {
        val binding = ItemChannelBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ChannelViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ChannelViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    companion object {
        class DiffCallback : DiffUtil.ItemCallback<Channel>() {
            override fun areItemsTheSame(
                oldItem: Channel,
                newItem: Channel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Channel,
                newItem: Channel
            ): Boolean {
                return oldItem == newItem
            }


        }
    }
}