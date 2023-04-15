package com.android.example.educationsupport.repository.firebase

import com.android.example.educationsupport.repository.entity.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseRepository @Inject constructor() {
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore = Firebase.firestore
    private val uemail = firebaseAuth?.currentUser?.email.toString()



    fun getFirebaseAuth(): FirebaseAuth {
        return firebaseAuth
    }

    fun getFireStore(): FirebaseFirestore {
        return firestore
    }

    fun saveUserProfile(usermap: HashMap<String, String>) {
        if (uemail != null) {
            firestore.collection("user").document(uemail).update(usermap as Map<String, Any>)
        }
    }

    suspend fun getUserProfileByCurrentUser(): DocumentSnapshot? {
        val ref = firestore.collection("user").document(uemail)
        val res = ref.get().await()
        return res
    }

}