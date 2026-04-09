package com.example.videoplayer_kt.presentation.playlist

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.videoplayer_kt.R
import com.example.videoplayer_kt.databinding.DialogAddPlaylistBinding
import com.example.videoplayer_kt.databinding.FragmentPlaylistBinding
import com.example.videoplayer_kt.domain.models.Playlist
import com.example.videoplayer_kt.domain.models.PlaylistType
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
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
                Snackbar.make(binding.root, "La playlist no tiene canales", Snackbar.LENGTH_SHORT).show()
            }
        },
        { playlist ->
            AlertDialog.Builder(requireContext())
                .setTitle(playlist.name)
                .setItems(arrayOf("Editar", "Eliminar")) {_, index ->
                    when (index) {
                        0 -> {
                            val dialogBinding = DialogAddPlaylistBinding.inflate(layoutInflater)
                            dialogBinding.etPlaylistName.setText(playlist.name)
                            dialogBinding.etPlaylistUrl.setText(playlist.url)
                            AlertDialog.Builder(requireContext())
                                .setTitle(playlist.name)
                                .setView(dialogBinding.root)
                                .setPositiveButton("Editar") {_,_ ->
                                    val updated = playlist.copy(
                                        name = dialogBinding.etPlaylistName.text.toString().trim(),
                                        url = dialogBinding.etPlaylistUrl.text.toString().trim()
                                    )
                                    viewModel.editPlaylist(updated)
                                }
                                .setNegativeButton("Cancelar") {_,_ ->
                                    null
                                }
                                .show()
                        }
                        1 -> {
                            AlertDialog.Builder(requireContext())
                                .setTitle("Eliminar ${playlist.name}")
                                .setMessage("¿Seguro que quiere eliminar la playlist?")
                                .setPositiveButton("Aceptar") {dialog, id ->
                                    viewModel.deletePlaylist(playlist)
                                }
                                .setNegativeButton("Cancelar") {dialog, id ->
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
}