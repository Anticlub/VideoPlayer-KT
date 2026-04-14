package com.example.videoplayer_kt.presentation.channel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.videoplayer_kt.R
import com.example.videoplayer_kt.databinding.ItemChannelBinding
import com.example.videoplayer_kt.domain.models.Channel

class ChannelAdapter(
    private val onChannelClick: (Channel) -> Unit,
    private val onFavoriteClick: (Channel) -> Unit
) : ListAdapter<Channel, ChannelAdapter.ChannelViewHolder>(DiffCallback()) {

    inner class ChannelViewHolder(
        private val binding: ItemChannelBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(channel: Channel){
            binding.tvChannelName.text = channel.name
            binding.tvChannelGroup.text = channel.group ?: binding.root.context.getString(R.string.no_group)
            binding.ivChannelIcon.load(channel.logo)
            binding.ivFavoriteIcon.setImageResource(
                if (channel.isFavorite) R.drawable.ic_favorite_yellow
                else R.drawable.ic_favorite
            )
            binding.root.setOnClickListener {
                onChannelClick(channel)
            }
            binding.ivFavoriteIcon.setOnClickListener {
                onFavoriteClick(channel)
            }
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