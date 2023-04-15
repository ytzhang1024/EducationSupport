package com.android.example.educationsupport.ui.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.android.example.educationsupport.ui.activities.EducatorHomeActivity
import com.android.example.educationsupport.ui.activities.StudentHomeActivity
import com.android.example.educationsupport.databinding.ActivitySignInBinding
import com.android.example.educationsupport.repository.firebase.FirebaseRepository
import com.android.example.educationsupport.viewModel.SignInViewModel


class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private val firebaseRepository = FirebaseRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        val viewModel = ViewModelProvider(this)[SignInViewModel::class.java]

        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                val ref = firebaseRepository.getFireStore().collection("user").document(email)
                firebaseRepository.getFirebaseAuth().signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        // Get role data from the database
                        ref.get().addOnSuccessListener {
                            val role = it.data?.get("role").toString()
                            if(role == "Student"){
                                val intent = Intent(this, StudentHomeActivity::class.java)
                                startActivity(intent)
                            }
                            else{
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
//    override fun onStart() {
//        super.onStart()
//
//        if(firebaseAuth.currentUser != null){
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }
//    }

}