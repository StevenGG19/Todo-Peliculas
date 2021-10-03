package com.steven.todopeliculas.repository.auth

import com.google.firebase.auth.FirebaseUser
import com.steven.todopeliculas.data.remote.AuthDataSource

class AuthRepositoryImpl(private val data: AuthDataSource) : AuthRepository {
    override suspend fun signUp(
        email: String,
        password: String,
        name: String,
        username: String
    ): FirebaseUser? = data.singUp(email, password, name, username)

    override suspend fun signIn(email: String, password: String): FirebaseUser? =
        data.singIn(email, password)
}