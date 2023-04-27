package com.android.example.educationsupport.data.repository

import com.android.example.educationsupport.data.model.User
import com.android.example.educationsupport.utils.UiState

interface FirebaseRepository {
    fun registerUser(email: String, password: String, user: User, result: (UiState<String>) -> Unit)
    fun updateUserInfo(user: User, result: (UiState<String>) -> Unit)
    fun loginUser(email: String, password: String, result: (UiState<String>) -> Unit)
    fun logout(result: () -> Unit)
}