package com.steven.todopeliculas.repository.profile

import com.steven.todopeliculas.data.model.User

interface ProfileRepo {
    suspend fun getUserData(): User?
    suspend fun updateUser(user: MutableMap<String, Any>)
    fun signOut()
}