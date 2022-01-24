package com.steven.todopeliculas.repository.auth

import com.google.firebase.auth.FirebaseUser
import com.steven.todopeliculas.data.remote.AuthDataSource
import javax.inject.Inject

class AuthRepository @Inject constructor(private val data: AuthDataSource) {
    suspend fun signUp(
        email: String,
        password: String,
        name: String,
        username: String
    ): FirebaseUser? = data.singUp(email, password, name, username)

    suspend fun signIn(email: String, password: String): FirebaseUser? =
        data.singIn(email, password)
}