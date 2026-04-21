package com.example.videoplayer_kt.presentation.playlist

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.videoplayer_kt.R
import com.example.videoplayer_kt.databinding.DialogAddPlaylistBinding
import com.example.videoplayer_kt.databinding.FragmentPlaylistBinding
import com.example.videoplayer_kt.domain.models.Playlist
import com.example.videoplayer_kt.domain.models.PlaylistType
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlaylistFragment: Fragment() {

    private var _binding: FragmentPlaylistBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PlaylistViewModel by viewModels()
    private val adapter = PlaylistAdapter(
        { playlist ->
            if(playlist.hasChannels){
                val action = PlaylistFragmentDirections
                    .actionPlaylistToChannel(playlist.id)
                findNavController().navigate(action)
            } else {
                Snackbar.make(binding.root, getString(R.string.playlist_no_channels), Snackbar.LENGTH_SHORT).show()
            }
        },
        { playlist ->
            AlertDialog.Builder(requireContext())
                .setTitle(playlist.name)
                .setItems(arrayOf(getString(R.string.action_edit), getString(R.string.action_delete))) {_, index ->
                    when (index) {
                        0 -> {
                            val dialogBinding = DialogAddPlaylistBinding.inflate(layoutInflater)
                            dialogBinding.etPlaylistName.setText(playlist.name)
                            dialogBinding.etPlaylistUrl.setText(playlist.url)
                            AlertDialog.Builder(requireContext())
                                .setTitle(playlist.name)
                                .setView(dialogBinding.root)
                                .setPositiveButton(getString(R.string.action_edit)) {_,_ ->
                                    val updated = playlist.copy(
                                        name = dialogBinding.etPlaylistName.text.toString().trim(),
                                        url = dialogBinding.etPlaylistUrl.text.toString().trim()
                                    )
                                    viewModel.editPlaylist(updated)
                                }
                                .setNegativeButton(getString(R.string.action_cancel)) {_,_ ->
                                    null
                                }
                                .show()
                        }
                        1 -> {
                            AlertDialog.Builder(requireContext())
                                .setTitle(getString(R.string.title_delete_playlist, playlist.name))
                                .setMessage(getString(R.string.confirm_delete_playlist))
                                .setPositiveButton(getString(R.string.action_accept)) {dialog, id ->
                                    viewModel.deletePlaylist(playlist)
                                }
                                .setNegativeButton(getString(R.string.action_cancel)) {dialog, id ->
                                    null
                                }
                                .show()
                        }
                    }
                }
                .show()
            true
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
        setupFab()
        setupMenu()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is PlaylistUiState.Loading -> {
                        binding.pbPlaylist.visibility = View.VISIBLE
                        binding.rvPlaylist.visibility = View.GONE
                        binding.tvErrorPlaylist.visibility = View.GONE
                    }
                    is PlaylistUiState.Success -> {
                        binding.pbPlaylist.visibility = View.GONE
                        binding.rvPlaylist.visibility = View.VISIBLE
                        binding.tvErrorPlaylist.visibility = View.GONE
                        adapter.submitList(state.playlists)
                    }
                    is PlaylistUiState.Error -> {
                        binding.pbPlaylist.visibility = View.GONE
                        binding.rvPlaylist.visibility = View.VISIBLE
                        binding.tvErrorPlaylist.visibility = View.VISIBLE
                        Snackbar.make(binding.root, state.message, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            combine(
                viewModel.lastChannelUrl,
                viewModel.lasChannelName,
                viewModel.lastChannelLogo,
                viewModel.lastChannelPlaylistName,
            ) { url, name, logo, playlistName ->
                LastChannelState(url,name, logo, playlistName)
            }.collect { state ->
                if (state.url.isNotEmpty()) {
                    binding.cvLastChannelPlaylist.visibility = View.VISIBLE
                    binding.tvLastChannelName.text = state.name
                    binding.ivLastChannelIcon.load(state.logo)
                    binding.tvLastChannelPlaylistname.text = state.playlistName

                    binding.cvLastChannelPlaylist.setOnClickListener {
                        val action = PlaylistFragmentDirections
                            .actionPlaylistToPlayer(state.url, state.name, state.logo, state.playlistName)
                        findNavController().navigate(action)
                    }
                } else {
                    binding.cvLastChannelPlaylist.visibility = View.GONE
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        binding.rvPlaylist.adapter = adapter
        binding.rvPlaylist.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupFab() {
        binding.fabAddPlaylist.setOnClickListener {
            // aquí abriremos el dialog
            showAddPlaylistDialog()
        }
    }

    private fun showAddPlaylistDialog() {
        val dialogBinding = DialogAddPlaylistBinding.inflate(layoutInflater)

        AlertDialog.Builder(requireContext())
            .setTitle(R.string.dialog_add_playlist_title)
            .setView(dialogBinding.root)
            .setPositiveButton(R.string.dialog_add) { _, _ ->
                val name = dialogBinding.etPlaylistName.text.toString().trim()
                val url = dialogBinding.etPlaylistUrl.text.toString().trim()

                if (name.isNotEmpty() && url.isNotEmpty()) {
                    val playlist = Playlist(
                        name = name,
                        url = url,
                        type = PlaylistType.M3U
                    )
                    viewModel.insertPlaylist(playlist)
                }
            }
            .setNegativeButton(R.string.dialog_cancel, null)
            .show()
    }

    private fun setupMenu() {
        binding.btnMenu.setOnClickListener { view ->
            val popup = PopupMenu(requireContext(), view)
            popup.menuInflater.inflate(R.menu.menu_playlist, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_logout -> {
                        viewModel.logout()
                        findNavController().navigate(R.id.action_playlist_to_login)
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
    }
}