package com.android.example.educationsupport.viewModel

import android.widget.RadioButton
import androidx.lifecycle.ViewModel
import com.android.example.educationsupport.databinding.ActivitySignUpBinding
import com.android.example.educationsupport.repository.firebase.FirebaseRepository

class SignUpViewModel : ViewModel() {
    private val firebaseRepository = FirebaseRepository()
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
                    firebaseRepository.getFirebaseAuth().createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val courseMap = hashMapOf(
                                "role" to role
                            )
                            firebaseRepository.getFireStore().collection("user").document(email).set(courseMap).addOnSuccessListener {
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