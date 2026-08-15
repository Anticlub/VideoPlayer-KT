package dev.anticlub.videoplayer.presentation.playlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.anticlub.videoplayer.R
import dev.anticlub.videoplayer.databinding.ItemPlaylistBinding
import dev.anticlub.videoplayer.domain.models.Playlist

class PlaylistAdapter(
    private val onPlaylistClick: (Playlist) -> Unit,
    private val onPlaylistLongClick: (Playlist) -> Boolean
) : ListAdapter<Playlist, PlaylistAdapter.PlaylistViewHolder>(DiffCallback()) {

    inner class PlaylistViewHolder(
        private val binding: ItemPlaylistBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(playlist: Playlist) {
            binding.tvPlaylistName.text = playlist.name
            binding.tvPlaylistUrl.text = playlist.url ?: binding.root.context.getString(R.string.local_file)
            binding.root.setOnClickListener {
                onPlaylistClick(playlist)
            }
            binding.root.setOnLongClickListener {
                onPlaylistLongClick(playlist)
            }
            binding.root.alpha = if (playlist.hasChannels) 1.0f else 0.5f
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val binding = ItemPlaylistBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PlaylistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        class DiffCallback : DiffUtil.ItemCallback<Playlist>() {
            override fun areItemsTheSame(
                oldItem: Playlist,
                newItem: Playlist
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Playlist,
                newItem: Playlist
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}