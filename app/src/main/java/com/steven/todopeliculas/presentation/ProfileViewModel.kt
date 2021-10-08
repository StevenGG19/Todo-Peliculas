package com.steven.todopeliculas.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.steven.todopeliculas.core.Resource
import com.steven.todopeliculas.repository.profile.ProfileRepo
import kotlinx.coroutines.Dispatchers

class ProfileViewModel(private val repo: ProfileRepo): ViewModel() {
    fun getDataUser() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repo.getUserData()))
        }catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun updateUser(user: MutableMap<String, Any>) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repo.updateUser(user)))
        }catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun signOut() = repo.signOut()
}
class ProfileViewModelFactory(private val repo: ProfileRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ProfileRepo::class.java).newInstance(repo)
    }
}