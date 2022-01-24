package com.steven.todopeliculas.repository.profile

import com.steven.todopeliculas.data.model.User
import com.steven.todopeliculas.data.remote.ProfileDataSource
import javax.inject.Inject

class ProfileRepo @Inject constructor(private val data: ProfileDataSource) {
    suspend fun getUserData(): User? = data.getUserData()
    suspend fun updateUser(user: MutableMap<String, Any>) = data.updateUser(user)
    fun signOut() = data.signOut()
}
