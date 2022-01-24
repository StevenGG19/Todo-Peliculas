package com.steven.todopeliculas.ui.profile

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.steven.todopeliculas.R
import com.steven.todopeliculas.core.Resource
import com.steven.todopeliculas.databinding.FragmentProfileBinding
import com.steven.todopeliculas.presentation.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var userName: String
    private lateinit var userNickname: String
    private lateinit var userEmail: String
    private var bitmap: Bitmap? = null
    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userData()
        update()
        signOut()
        binding.btnChangePhoto.setOnClickListener {
            dispatchTakePictureIntent()
        }
    }

    private fun signOut() {
        binding.btnExit.setOnClickListener {
            viewModel.signOut()
            findNavController().navigate(R.id.action_profieFragment_to_loginFragment)
        }
    }

    private fun update() {
        binding.btnUpdate.setOnClickListener {
            val name = binding.tdUserName.text.toString().trim()
            val nickname = binding.tdNickname.text.toString().trim()
            val email = binding.tdEmail.text.toString().trim()
            if(validateUser(name, nickname, email)) return@setOnClickListener
            changeData(name, nickname, email)
        }
    }

    private fun changeData(name: String, nickname: String, email: String) {
        val userData = mutableMapOf<String, Any>()
        if (userName != name) {
            userData["fullName"] = name
        }
        if (userNickname != nickname) {
            userData["username"] = nickname
        }
        if (userEmail != email) {
            userData["email"] = email
        }
        if (bitmap != null) {
            userData["photoUrl"] = bitmap!!
            bitmap = null
        }
        if (userData.isNotEmpty()) {
            viewModel.updateUser(userData).observe(viewLifecycleOwner, { result ->
                when(result) {
                    is Resource.Success -> {
                        Toast.makeText(requireContext(), "Se han actualizado los datos", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Failure -> {
                        Toast.makeText(requireContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    private fun validateUser(name: String, nickname: String, email: String): Boolean {
        return when {
            name.isEmpty() -> {
                binding.userName.error = "Name is empty"
                true
            }
            nickname.isEmpty() -> {
                binding.nickname.error = "Nickname is empty"
                true
            }
            email.isEmpty() -> {
                binding.email.error = "E-mail is empty"
                true
            }
            else -> {
                false
            }
        }
    }

    private fun userData() {
        viewModel.getDataUser().observe(viewLifecycleOwner, { user ->
            when(user) {
                is Resource.Loading -> {
                    binding.btnChangePhoto.isEnabled = false
                    binding.btnExit.isEnabled = false
                    binding.btnUpdate.isEnabled = false
                }
                is Resource.Success -> {
                    binding.btnChangePhoto.isEnabled = true
                    binding.btnExit.isEnabled = true
                    binding.btnUpdate.isEnabled = true
                    user.data?.let {
                        Glide.with(requireContext()).load(it.photoUrl).into(binding.cimgPhoto)
                        userName = it.fullName
                        userNickname = it.username
                        userEmail = it.email
                        binding.tdUserName.setText(it.fullName)
                        binding.tdNickname.setText(it.username)
                        binding.tdEmail.setText(it.email)
                    }
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private val result =
        registerForActivityResult(StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageBitmap = result.data?.extras?.get("data") as Bitmap
                binding.cimgPhoto.setImageBitmap(imageBitmap)
                bitmap = imageBitmap
            }
        }

    private fun dispatchTakePictureIntent() {
        try {
            result.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                requireContext(),
                "No se encontro una app para tomar fotos",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}