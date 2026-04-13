package com.example.videoplayer_kt.presentation.channel

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.videoplayer_kt.databinding.FragmentChannelBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.videoplayer_kt.R
import com.example.videoplayer_kt.domain.models.Channel
import com.google.android.material.chip.Chip

@AndroidEntryPoint
class ChannelFragment: Fragment() {
    private var playlistId: Long = -1L
    private var currentPlaylistName: String = ""
    private var _binding: FragmentChannelBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ChannelViewModel by viewModels()
    private var favoritesChip: Chip? = null
    private val adapter = ChannelAdapter(
        {channel ->
            val action = ChannelFragmentDirections
                .actionChannelToPlayer(channel.url, channel.name, channel.logo?: "", currentPlaylistName)
        },
        { channel ->
            viewModel.toggleFavorite(channel)
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChannelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playlistId = arguments?.getLong("playlistId") ?: -1L
        viewModel.loadChannel(playlistId)
        setupRecyclerView()
        setupSearchBar()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView(){
        binding.rvChannels.adapter = adapter
        binding.rvChannels.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is ChannelUiState.Loading -> {
                        binding.pbChannels.visibility = View.VISIBLE
                        binding.rvChannels.visibility = View.GONE
                        binding.tvErrorChannels.visibility = View.GONE
                    }
                    is ChannelUiState.Success -> {
                        binding.pbChannels.visibility = View.GONE
                        binding.rvChannels.visibility = View.VISIBLE
                        binding.tvErrorChannels.visibility = View.GONE
                        adapter.submitList(state.channels)
                        setupChipGroup(state.channels)
                    }
                    is ChannelUiState.Error -> {
                        binding.pbChannels.visibility = View.GONE
                        binding.rvChannels.visibility = View.GONE
                        binding.tvErrorChannels.visibility = View.VISIBLE
                        binding.tvErrorChannels.text = state.message
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.playlistName.collect { name ->
                currentPlaylistName = name
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.favoritesChannel.collect{ favorites ->
                if (favorites.isEmpty()){
                    favoritesChip?.visibility = View.GONE
                } else {
                    favoritesChip?.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setupSearchBar() {
        binding.etSearchChannel.addTextChangedListener { text ->
            viewModel.searchChannels(text.toString())
        }
    }

    private fun setupChipGroup(channels: List<Channel>) {
        val groups = channels.mapNotNull { it.group }.distinct()
        val hasFavorites = channels.any { it.isFavorite }

        binding.chipGroupCategories.removeAllViews()

        // Chip "Todos"
        val allChip = Chip(requireContext(), null, com.google.android.material.R.attr.chipStyle).apply {
            text = context.getString(R.string.all)
            isCheckable = true
            isChecked = true
        }
        allChip.setOnClickListener { viewModel.filterByGroup(null) }
        binding.chipGroupCategories.addView(allChip)

        // Chip "Favoritos"
        if (favoritesChip == null) {
            favoritesChip = Chip(requireContext(), null, com.google.android.material.R.attr.chipStyle).apply {
                text = context.getString(R.string.favorites)
                isCheckable = true
            }
            favoritesChip?.setOnClickListener { viewModel.filterFavorites() }
        }
        favoritesChip?.visibility = if (hasFavorites) View.VISIBLE else View.GONE
        binding.chipGroupCategories.addView(favoritesChip)

        // Chips de grupos
        groups.forEach { group ->
            val chip = Chip(requireContext(), null, com.google.android.material.R.attr.chipStyle).apply {
                text = group
                isCheckable = true
            }
            chip.setOnClickListener { viewModel.filterByGroup(group) }
            binding.chipGroupCategories.addView(chip)
        }
    }

}