package com.steven.todopeliculas.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.steven.todopeliculas.core.Resource
import com.steven.todopeliculas.data.local.entities.UserEntity
import com.steven.todopeliculas.repository.profile.ProfileRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repo: ProfileRepo) : ViewModel() {

    var userInfo: LiveData<UserEntity> = repo.dataLocal.getUser()

    fun getDataUser() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val userData = repo.getUserData()
            emit(Resource.Success(userData))
            userData?.let { data ->
                val user = UserEntity(data)
                repo.dataLocal.saveUser(user)
            }
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun updateUser(user: MutableMap<String, Any>) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repo.updateUser(user)))
            repo.getUserData()?.let {
                val userInfo = UserEntity(it)
                updateUser(userInfo)
            }
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun signOut() = repo.signOut()

    fun deleteUser() = viewModelScope.launch(Dispatchers.IO) {
        repo.dataLocal.deleteUser()
    }

    private fun updateUser(userEntity: UserEntity) = viewModelScope.launch(Dispatchers.IO) {
        repo.dataLocal.updateUser(userEntity)
    }
}