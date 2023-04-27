package com.android.example.educationsupport.ui.base

import android.widget.RadioButton
import androidx.lifecycle.ViewModel
import com.android.example.educationsupport.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUpViewModel : ViewModel() {
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore = Firebase.firestore
    private val uemail = firebaseAuth?.currentUser?.email.toString()
    private var Info: String = ""

    fun UserSignUp(binding: ActivitySignUpBinding): String {
        val email = binding.emailEt.text.toString()
        val pass = binding.passET.text.toString()
        val confirmPass = binding.confirmPassEt.text.toString()
        var role = ""
        val button_educator = binding.role.getChildAt(0) as (RadioButton)
        if (button_educator.isChecked) {
            role = "Student"
        } else {
            role = "Educator"
        }
            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass) {
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val courseMap = hashMapOf(
                                "role" to role
                            )
                            firestore.collection("user").document(email).set(courseMap).addOnSuccessListener {
                                Info = "Adding Successful"
                            }.addOnFailureListener{
                                Info = "Adding Failure"
                            }
                            Info = "Register Successful"

                        } else {
                            Info = it.exception.toString()
                        }
                    }
                } else {
                    Info = "Password is not matching"
                }
            } else {
                Info = "Empty Fields Are not Allowed !!"
            }
        return Info
    }
}