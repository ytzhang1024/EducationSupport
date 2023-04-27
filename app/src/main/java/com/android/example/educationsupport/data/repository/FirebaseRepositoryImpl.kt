package com.android.example.educationsupport.data.repository

import android.content.SharedPreferences
import com.android.example.educationsupport.data.model.User
import com.android.example.educationsupport.utils.UiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseRepositoryImpl(
    val auth: FirebaseAuth,
    val database: FirebaseFirestore
) : FirebaseRepository {
    override fun registerUser(
        email: String,
        password: String,
        user: User,
        result: (UiState<String>) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun updateUserInfo(user: User, result: (UiState<String>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun loginUser(email: String, password: String, result: (UiState<String>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun logout(result: () -> Unit) {
        TODO("Not yet implemented")
    }

}