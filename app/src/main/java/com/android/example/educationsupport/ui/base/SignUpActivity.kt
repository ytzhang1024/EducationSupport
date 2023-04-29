package com.android.example.educationsupport.ui.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.android.example.educationsupport.data.model.User
import com.android.example.educationsupport.databinding.ActivitySignUpBinding
import com.android.example.educationsupport.utils.UiState
import com.android.example.educationsupport.utils.hide
import com.android.example.educationsupport.utils.isValidEmail
import com.android.example.educationsupport.utils.show
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    val TAG: String = "SignUpActivity"
    lateinit var binding: ActivitySignUpBinding
    val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observer()
        binding.button.setOnClickListener {
//            if (validation()){
                viewModel.register(
                    email = binding.emailEt.text.toString(),
                    password = binding.passET.text.toString(),
                    user = getUserObj()
                )
                finish()
//            }
        }
    }


    fun observer() {
        viewModel.register.observe(this) { state ->
            when(state){
                is UiState.Loading -> {
                    binding.button.setText("")
                    binding.registerProgress.show()
                }
                is UiState.Failure -> {
                    binding.button.setText("Register")
                    binding.registerProgress.hide()
                    Toast.makeText(this, state.error, Toast.LENGTH_SHORT).show()

                }
                is UiState.Success -> {
                    binding.button.setText("Register")
                    binding.registerProgress.hide()
                    Toast.makeText(this, state.data, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, SignInActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    fun getUserObj(): User {
        var role = ""
        val button_educator = binding.role.getChildAt(0) as (RadioButton)
        if (button_educator.isChecked) {
            role = "Student"
        } else {
            role = "Tutor"
        }
        return User(
            id = "",
            firstName = binding.firstNameEt.text.toString(),
            lastName = binding.lastNameEt.text.toString(),
            email = binding.emailEt.text.toString(),
            role = role
        )
    }

    fun validation(): Boolean {
        var isValid = true

        if (binding.firstNameEt.text.isNullOrEmpty()){
            isValid = false
        }

        if (binding.lastNameEt.text.isNullOrEmpty()){
            isValid = false
        }

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

//    private lateinit var binding: ActivitySignUpBinding
//    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
//    private val firestore = Firebase.firestore
//    private val uemail = firebaseAuth?.currentUser?.email.toString()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivitySignUpBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
//
//        binding.textView.setOnClickListener {
//            val intent = Intent(this, SignInActivity::class.java)
//            startActivity(intent)
//        }
//
//        binding.button.setOnClickListener {
//            val Info = viewModel.UserSignUp(binding)
//            Toast.makeText(this, Info, Toast.LENGTH_SHORT).show()
//            if (Info == "Register Successful") {
//                val intent = Intent(this, SignInActivity::class.java)
//                startActivity(intent)
//            }
//        }
//    }
}