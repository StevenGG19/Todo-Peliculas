package com.steven.todopeliculas.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.steven.todopeliculas.R
import com.steven.todopeliculas.core.Resource
import com.steven.todopeliculas.data.remote.AuthDataSource
import com.steven.todopeliculas.databinding.FragmentLoginBinding
import com.steven.todopeliculas.presentation.AuthViewModel
import com.steven.todopeliculas.presentation.AuthViewModelFactory
import com.steven.todopeliculas.repository.auth.AuthRepositoryImpl


class LoginFragment : Fragment(R.layout.fragment_login) {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val viewmodel by viewModels<AuthViewModel> {
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
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isUserLoggedIn()
        goToSignUpPage()
        doLogin()
    }

    private fun isUserLoggedIn() {
        firebaseAuth.currentUser?.let {
            findNavController().navigate(R.id.action_loginFragment_to_movieFragment)
        }
    }

    private fun doLogin() {
        binding.btnLogin.setOnClickListener {
            val email = binding.tdEmail.text.toString().trim()
            val password = binding.tdPassword.text.toString().trim()
            if (validateUserData(email, password)) return@setOnClickListener
            singIn(email, password)
        }
    }

    private fun goToSignUpPage() {
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun singIn(email: String, password: String) {
        viewmodel.singIn(email, password).observe(viewLifecycleOwner, { result ->
            when (result) {
                is Resource.Loading -> binding.btnLogin.isEnabled = false
                is Resource.Success -> findNavController().navigate(R.id.action_loginFragment_to_movieFragment)
                is Resource.Failure -> {
                    binding.btnLogin.isEnabled = true
                    Toast.makeText(requireContext(), "Error al iniciar sesion", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        })
    }

    private fun validateUserData(email: String, password: String): Boolean {
        return when {
            email.isEmpty() -> {
                binding.email.error = "E-mail is empty"
                true
            }
            password.isEmpty() -> {
                binding.password.error = "Password is empty"
                true
            }
            else -> {
                false
            }
        }
    }

}