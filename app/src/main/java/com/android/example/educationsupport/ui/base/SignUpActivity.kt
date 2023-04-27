package com.android.example.educationsupport.ui.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.android.example.educationsupport.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore = Firebase.firestore
    private val uemail = firebaseAuth?.currentUser?.email.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]

        binding.textView.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener {
            val Info = viewModel.UserSignUp(binding)
            Toast.makeText(this, Info, Toast.LENGTH_SHORT).show()
            if (Info == "Register Successful") {
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
            }
        }
    }
}