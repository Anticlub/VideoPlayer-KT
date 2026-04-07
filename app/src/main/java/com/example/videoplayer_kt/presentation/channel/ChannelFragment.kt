package com.example.videoplayer_kt.presentation.channel

import android.os.Bundle
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

@AndroidEntryPoint
class ChannelFragment: Fragment() {
    private var playlistId: Long = -1L
    private var _binding: FragmentChannelBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ChannelViewModel by viewModels()
    private val adapter = ChannelAdapter()

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
    }
}