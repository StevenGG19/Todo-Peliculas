package com.steven.todopeliculas.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.steven.todopeliculas.application.AppConstants
import com.steven.todopeliculas.data.model.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthDataSource @Inject constructor(){
    suspend fun singUp(email: String, password: String, fullName: String, username: String): FirebaseUser? {
        val authResult = FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await()
        authResult.user?.uid?.let {
            FirebaseFirestore.getInstance().collection("users").document(it).set(User(email, fullName, username, AppConstants.USER_URL)).await()
        }
        return authResult.user
    }

    suspend fun singIn(email: String, password: String): FirebaseUser? {
        val authResult = FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
        return authResult.user
    }
}