package com.android.example.educationsupport.ui.base

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.android.example.educationsupport.databinding.ActivitySignInBinding
import com.android.example.educationsupport.ui.home.EducatorHomeActivity
import com.android.example.educationsupport.ui.home.StudentHomeActivity
import com.android.example.educationsupport.utils.isValidEmail
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {
    val TAG: String = "SignInActivity"
    lateinit var binding: ActivitySignInBinding
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore = Firebase.firestore
    val viewModel: AuthViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

//        binding.button.setOnClickListener {
//            if (validation()) {
//                viewModel.login(
//                    email = binding.emailEt.text.toString(),
//                    password = binding.passET.text.toString()
//                )
//                checkRole()
//            }
//        }

        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                val ref = firestore.collection("user").document(email)
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        // Get role data from the database
                        ref.get().addOnSuccessListener {
                            val role = it.data?.get("role").toString()
                            if (role == "Student") {
                                val intent = Intent(this, StudentHomeActivity::class.java)
                                startActivity(intent)
                            } else {
                                val intent = Intent(this, EducatorHomeActivity::class.java)
                                startActivity(intent)
                            }
                        }
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }

        }
    }


    // 登录的缓存 login cache
    override fun onStart() {
        super.onStart()
        if(firebaseAuth.currentUser != null){
            val intent = Intent(this, StudentHomeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    fun validation(): Boolean {
        var isValid = true

        if (binding.emailEt.text.isNullOrEmpty()){
            isValid = false
        }else{
            if (!binding.emailEt.text.toString().isValidEmail()){
                isValid = false
            }
        }
        if (binding.passET.text.isNullOrEmpty()){
            isValid = false
        }else{
            if (binding.passET.text.toString().length < 8){
                isValid = false
            }
        }
        return isValid
    }

}
