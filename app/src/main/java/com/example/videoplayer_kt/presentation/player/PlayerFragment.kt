package com.example.videoplayer_kt.presentation.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.example.videoplayer_kt.R
import com.example.videoplayer_kt.databinding.FragmentPlayerBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@UnstableApi
@AndroidEntryPoint
class PlayerFragment: Fragment() {

    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PlayerViewModel by viewModels()
    private var player: ExoPlayer? = null
    private var currentResizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT

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
        val streamName = args.streamName
        val streamLogo = args.streamLogo
        val streamPlyalistName = args.streamPlaylistName
        viewModel.initPlayer(streamUrl, streamName, streamLogo, streamPlyalistName)
        binding.btnAspectRatio.setOnClickListener {
            currentResizeMode = when (currentResizeMode) {

                AspectRatioFrameLayout.RESIZE_MODE_FIT -> AspectRatioFrameLayout.RESIZE_MODE_FILL
                AspectRatioFrameLayout.RESIZE_MODE_FILL -> AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                else -> AspectRatioFrameLayout.RESIZE_MODE_FIT
            }
            binding.playerView.resizeMode = currentResizeMode
        }
        setupPlayer(streamUrl)
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        exitFullScreen()
        player?.release()
        player = null
        _binding = null
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
        player?.addListener(createPlayerListener())
        binding.playerView.player = player
        binding.playerView.setFullscreenButtonClickListener { isFullScreen ->
            if (isFullScreen) {
                enterFullScreen()
            } else {
                exitFullScreen()
            }
        }
        binding.playerView.setControllerVisibilityListener(
            PlayerView.ControllerVisibilityListener { visibility ->
                _binding?.btnAspectRatio?.visibility = visibility
            }
        )
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
                        binding.playerView.visibility = View.GONE
                    }
                    is PlayerUiState.Playing -> {
                        binding.pbPlayer.visibility = View.GONE
                        binding.playerView.visibility = View.VISIBLE
                    }
                    is PlayerUiState.Ended -> {
                        binding.playerView.visibility = View.GONE
                        binding.pbPlayer.visibility = View.GONE
                        binding.tvInfoPlayer.text = ""
                    }
                    is PlayerUiState.Error -> {
                        binding.playerView.visibility = View.GONE
                        binding.pbPlayer.visibility = View.GONE
                        binding.tvInfoPlayer.text = state.message
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

    private fun createPlayerListener() = object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int){
            when (playbackState) {
                Player.STATE_BUFFERING -> viewModel.onBuffering()
                Player.STATE_READY -> viewModel.onPlaying()
                Player.STATE_ENDED -> {viewModel.onEnded()}
                Player.STATE_IDLE -> {}
            }
        }
        override fun onPlayerError(error: PlaybackException) {
            viewModel.onError(error.localizedMessage ?: requireContext().getString(R.string.unknown_error))
        }
    }
}