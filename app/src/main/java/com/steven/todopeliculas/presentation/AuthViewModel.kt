package com.steven.todopeliculas.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.steven.todopeliculas.core.Resource
import com.steven.todopeliculas.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repo: AuthRepository) : ViewModel() {
    fun singUp(email: String, password: String, name: String, username: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                emit(Resource.Success(repo.signUp(email, password, name, username)))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }

    fun singIn(email: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repo.signIn(email, password)))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}