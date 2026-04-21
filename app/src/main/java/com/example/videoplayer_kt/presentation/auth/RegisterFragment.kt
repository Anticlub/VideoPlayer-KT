package com.example.videoplayer_kt.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.videoplayer_kt.R
import com.example.videoplayer_kt.databinding.FragmentRegisterBinding
import com.example.videoplayer_kt.domain.models.AuthErrorType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupListener() {
        binding.btnRegister.setOnClickListener {
            val email = binding.etRegisterEmail.text.toString().trim()
            val password = binding.etRegisterPassword.text.toString()
            val passwordConfirm = binding.etRegisterPasswordConfirm.text.toString()

            viewModel.register(email, password, passwordConfirm)
        }

        binding.tvGoToLogin.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is AuthUiState.Idle -> renderIdle()
                    is AuthUiState.Loading -> renderLoading()
                    is AuthUiState.Success -> navigateToPlaylist()
                    is AuthUiState.Error -> renderError(state.type)
                }
            }
        }
    }

    private fun renderIdle() {
        binding.pbRegister.visibility = View.GONE
        binding.btnRegister.isEnabled = true
        binding.tvRegisterError.visibility = View.GONE
    }

    private fun renderLoading() {
        binding.pbRegister.visibility = View.VISIBLE
        binding.btnRegister.isEnabled = false
        binding.tvRegisterError.visibility = View.GONE
    }

    private fun renderError(type: AuthErrorType) {
        binding.pbRegister.visibility = View.GONE
        binding.btnRegister.isEnabled = true
        showError(getString(mapErrorToString(type)))
    }

    private fun mapErrorToString(type: AuthErrorType): Int {
        return when (type) {
            AuthErrorType.PASSWORD_DONT_MATCH -> R.string.auth_error_passwords_dont_match
            AuthErrorType.EMPTY_FIELDS -> R.string.auth_error_empty_fields
            AuthErrorType.INVALID_CREDENTIALS -> R.string.auth_error_invalid_credentials
            AuthErrorType.USER_NOT_FOUND -> R.string.auth_error_user_not_found
            AuthErrorType.NETWORK_ERROR -> R.string.auth_error_network
            AuthErrorType.EMAIL_ALREADY_IN_USE -> R.string.auth_error_email_already_in_use
            AuthErrorType.WEAK_PASSWORD -> R.string.auth_error_weak_password
            AuthErrorType.INVALID_EMAIL -> R.string.auth_error_invalid_email
            AuthErrorType.UNKNOWN -> R.string.auth_error_unknown
        }
    }


    private fun showError(message: String) {
        binding.tvRegisterError.text = message
        binding.tvRegisterError.visibility = View.VISIBLE
    }

    private fun navigateToLogin() {
        findNavController().navigate(R.id.action_register_to_login)
    }

    private fun navigateToPlaylist() {
        findNavController().navigate(R.id.action_register_to_playlist)
    }
}