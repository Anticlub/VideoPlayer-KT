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
import com.example.videoplayer_kt.databinding.FragmentLoginBinding
import com.example.videoplayer_kt.domain.models.AuthErrorType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
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
        binding.btnLogin.setOnClickListener {
            val email = binding.etLoginEmail.text.toString().trim()
            val password = binding.etLoginPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                binding.tvLoginError.text = getString(R.string.auth_error_empty_fields)
                binding.tvLoginError.visibility = View.VISIBLE
                return@setOnClickListener
            }

            viewModel.login(email, password)
        }

        binding.tvGoToRegister.setOnClickListener {
            navigateToRegister()
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
        binding.pbLogin.visibility = View.GONE
        binding.btnLogin.isEnabled = true
        binding.tvLoginError.visibility = View.GONE
    }

    private fun renderLoading() {
        binding.pbLogin.visibility = View.VISIBLE
        binding.btnLogin.isEnabled = false
        binding.tvLoginError.visibility = View.GONE
    }

    private fun renderError(type: AuthErrorType) {
        binding.pbLogin.visibility = View.GONE
        binding.btnLogin.isEnabled = true
        binding.tvLoginError.visibility = View.VISIBLE
        binding.tvLoginError.text = getString(mapErrorToString(type))
    }

    private fun mapErrorToString(type: AuthErrorType): Int {
        return when (type) {
            AuthErrorType.INVALID_CREDENTIALS -> R.string.auth_error_invalid_credentials
            AuthErrorType.USER_NOT_FOUND -> R.string.auth_error_user_not_found
            AuthErrorType.NETWORK_ERROR -> R.string.auth_error_network
            AuthErrorType.EMAIL_ALREADY_IN_USE -> R.string.auth_error_email_already_in_use
            AuthErrorType.WEAK_PASSWORD -> R.string.auth_error_weak_password
            AuthErrorType.INVALID_EMAIL -> R.string.auth_error_invalid_email
            AuthErrorType.UNKNOWN -> R.string.auth_error_unknown
        }
    }

    private fun navigateToRegister() {
        findNavController().navigate(R.id.action_login_to_register)
    }

    private fun navigateToPlaylist() {
        findNavController().navigate(R.id.action_login_to_playlist)
    }

}