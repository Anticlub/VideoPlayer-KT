package dev.anticlub.videoplayer.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dev.anticlub.videoplayer.R
import dev.anticlub.videoplayer.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.destination.collect { destination ->
                when (destination) {
                    SplashDestination.Login -> navigateToLogin()
                    SplashDestination.Playlist -> navigateToPlaylist()
                    null -> {

                    }
                }
            }
        }
    }

    private fun navigateToPlaylist() {
        findNavController().navigate(R.id.action_splash_to_playlist)
    }

    private fun navigateToLogin() {
        findNavController().navigate(R.id.action_splash_to_login)
    }
}