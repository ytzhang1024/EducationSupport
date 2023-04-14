package com.android.example.educationsupport.ui.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.android.example.educationsupport.ui.activities.EducatorHomeActivity
import com.android.example.educationsupport.ui.activities.StudentHomeActivity
import com.android.example.educationsupport.databinding.ActivitySignInBinding
import com.android.example.educationsupport.viewModel.SignInViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

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
            val Info = viewModel.UserSignIn(binding)

            if (Info == "Student") {
                val intent = Intent(this, StudentHomeActivity::class.java)
                startActivity(intent)
            } else if (Info == "Educator") {
                val intent = Intent(this, EducatorHomeActivity::class.java)
                startActivity(intent)
            }

            Toast.makeText(this, Info, Toast.LENGTH_SHORT).show()
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