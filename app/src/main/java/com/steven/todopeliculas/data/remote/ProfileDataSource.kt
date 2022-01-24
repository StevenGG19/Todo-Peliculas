package com.steven.todopeliculas.data.remote

import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.steven.todopeliculas.data.model.User
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.*
import javax.inject.Inject

class ProfileDataSource @Inject constructor(){
    suspend fun getUserData(): User? {
        val userId = FirebaseAuth.getInstance().currentUser
        var dataUser: User? = null
        userId?.uid?.let {
            val database =
                FirebaseFirestore.getInstance().collection("users").document(it).get().await()
            dataUser = database.toObject(User::class.java)

        }
        return dataUser
    }

    suspend fun updateUser(user: MutableMap<String, Any>) {
        val userId = FirebaseAuth.getInstance().currentUser
        user["photoUrl"]?.let {
            val randomName = UUID.randomUUID().toString()
            val storageRef =
                FirebaseStorage.getInstance().reference.child("${userId?.uid}/photo/$randomName")
            val baos = ByteArrayOutputStream()
            it as Bitmap
            it.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            user["photoUrl"] =
                storageRef.putBytes(baos.toByteArray()).await().storage.downloadUrl.await().toString()
        }
        userId?.uid?.let {
            FirebaseFirestore.getInstance().collection("users").document(it).update(user).await()
        }
    }

    fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }
}