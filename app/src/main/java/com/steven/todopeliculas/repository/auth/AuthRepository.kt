package com.steven.todopeliculas.repository.auth

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun signUp(email: String, password: String, name: String, username: String): FirebaseUser?
    suspend fun signIn(email: String, password: String): FirebaseUser?
}