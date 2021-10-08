package com.steven.todopeliculas.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.steven.todopeliculas.R
import com.steven.todopeliculas.core.Resource
import com.steven.todopeliculas.data.remote.AuthDataSource
import com.steven.todopeliculas.databinding.FragmentRegisterBinding
import com.steven.todopeliculas.presentation.AuthViewModel
import com.steven.todopeliculas.presentation.AuthViewModelFactory
import com.steven.todopeliculas.repository.auth.AuthRepositoryImpl

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepositoryImpl(
                AuthDataSource()
            )
        )
    }

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
        singUp()
    }

    private fun singUp() {
        binding.singUp.setOnClickListener {
            val email = binding.tdEmail.text.toString().trim()
            val password = binding.tdPassword.text.toString().trim()
            val name = binding.tdUserName.text.toString().trim()
            val nickname = binding.tdNickname.text.toString().trim()
            val confirmPassword = binding.tdConfirmPassword.text.toString().trim()
            clearError()
            if (validateUserData(
                    name,
                    nickname,
                    email,
                    password,
                    confirmPassword
                )
            ) return@setOnClickListener
            createUser(
                name,
                nickname,
                email,
                password
            )
        }
    }

    private fun createUser(
        name: String,
        nickname: String,
        email: String,
        password: String
    ) {
        viewModel.singUp(email, password, name, nickname).observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> binding.singUp.isEnabled = false
                is Resource.Success -> findNavController().navigate(R.id.action_registerFragment_to_movieFragment)
                is Resource.Failure -> {
                    binding.singUp.isEnabled = true
                    Toast.makeText(requireContext(), "Error al registrarse", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }


    private fun clearError() {
        binding.userName.error = null
        binding.nickname.error = null
        binding.email.error = null
        binding.password.error = null
        binding.confirmPassword.error = null
    }

    private fun validateUserData(
        name: String,
        nickname: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        return if (name.isEmpty()) {
            binding.userName.error = "Name is empty"
            true
        } else if (nickname.isEmpty()) {
            binding.nickname.error = "Nickname is empty"
            true
        } else if (email.isEmpty()) {
            binding.email.error = "E-mail is empty"
            true
        } else if (password.isEmpty()) {
            binding.password.error = "Password is empty"
            true
        } else if (confirmPassword.isEmpty()) {
            binding.confirmPassword.error = "Confirm Password is empty"
            true
        } else if (password != confirmPassword) {
            binding.password.error = "Passwords do not match"
            true
        } else if (password.length < 8) {
            binding.password.error = "The password must have a minimum of 8 characters"
            true
        } else {
            false
        }
    }


}