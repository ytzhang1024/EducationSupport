package com.android.example.educationsupport.viewModel

import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.android.example.educationsupport.databinding.ActivitySignInBinding
import com.android.example.educationsupport.repository.firebase.FirebaseRepository
import com.android.example.educationsupport.ui.activities.EducatorHomeActivity
import com.android.example.educationsupport.ui.activities.StudentHomeActivity

class SignInViewModel : ViewModel() {
    private val firebaseRepository = FirebaseRepository()
    private var Info: String = ""

    fun UserSignIn(binding: ActivitySignInBinding): String {
        val email = binding.emailEt.text.toString()
        val pass = binding.passET.text.toString()
        val ref = firebaseRepository.getFireStore().collection("user").document(email)

        if (email.isNotEmpty() && pass.isNotEmpty()) {

            firebaseRepository.getFirebaseAuth().signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                if (it.isSuccessful) {
                    // Get role data from the database
                    ref.get().addOnSuccessListener {
                        val role = it.data?.get("role").toString()
                        if(role == "Student") {
                            Info = "Student"
                        }
                        else {
                            Info = "Educator"
                        }
                    }
                } else {
                    Info = it.exception.toString()
                }
            }
        } else {
            Info = "Empty Fields Are not Allowed !!"
        }

        return Info
    }
}