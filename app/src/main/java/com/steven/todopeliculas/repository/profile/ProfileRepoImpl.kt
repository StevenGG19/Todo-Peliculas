package com.steven.todopeliculas.repository.profile

import com.steven.todopeliculas.data.model.User
import com.steven.todopeliculas.data.remote.ProfileDataSource

class ProfileRepoImpl(private val data: ProfileDataSource): ProfileRepo {
    override suspend fun getUserData(): User? = data.getUserData()
    override suspend fun updateUser(user: MutableMap<String, Any>) = data.updateUser(user)
    override fun signOut() = data.signOut()
}
