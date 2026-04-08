package com.example.videoplayer_kt.presentation.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.videoplayer_kt.databinding.FragmentPlayerBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlayerFragment: Fragment() {

    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PlayerViewModel by viewModels()
    private var player: ExoPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = PlayerFragmentArgs.fromBundle(requireArguments())
        val streamUrl = args.streamUrl
        viewModel.initPlayer(streamUrl)
        setupPlayer(streamUrl)
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        player?.release()
        player = null
        _binding = null
        exitFullScreen()
    }

    override fun onPause() {
        super.onPause()
        player?.pause()
        exitFullScreen()
    }

    override fun onResume() {
        super.onResume()
        player?.play()
        enterFullScreen()
    }

    private fun setupPlayer(url: String){
        player = ExoPlayer.Builder(requireContext()).build()
        binding.playerView.player = player
        binding.playerView.setFullscreenButtonClickListener { isFullScreen ->
            if (isFullScreen) {
                enterFullScreen()
            } else {
                exitFullScreen()
            }
        }
        val mediaItem = MediaItem.fromUri(url)
        player?.setMediaItem(mediaItem)
        player?.prepare()
        player?.playWhenReady = true
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state){
                    is PlayerUiState.Loading -> {
                        binding.pbPlayer.visibility = View.VISIBLE
                    }
                    is PlayerUiState.Playing -> {
                        binding.pbPlayer.visibility = View.GONE
                    }
                    is PlayerUiState.Error -> {
                        binding.pbPlayer.visibility = View.GONE
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }

    private fun enterFullScreen(){
        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, false)
        val controller = WindowInsetsControllerCompat(requireActivity().window, binding.root)
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }

    private fun exitFullScreen(){
        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, true)
        val controller = WindowInsetsControllerCompat(requireActivity().window, binding.root)
        controller.show(WindowInsetsCompat.Type.systemBars())
    }
}