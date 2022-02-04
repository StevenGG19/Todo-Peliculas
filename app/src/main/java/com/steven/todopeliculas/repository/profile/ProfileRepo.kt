package com.steven.todopeliculas.repository.profile

import com.steven.todopeliculas.data.local.LocalDataSource
import com.steven.todopeliculas.data.model.User
import com.steven.todopeliculas.data.remote.ProfileDataSource
import javax.inject.Inject

class ProfileRepo @Inject constructor(
    private val data: ProfileDataSource,
    local: LocalDataSource) {
    val dataLocal = local
    suspend fun getUserData(): User? = data.getUserData()
    suspend fun updateUser(user: MutableMap<String, Any>) = data.updateUser(user)
    fun signOut() = data.signOut()
}
