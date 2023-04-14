package com.android.example.educationsupport.ui.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.example.educationsupport.R
import com.android.example.educationsupport.databinding.ActivityCreateActBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CreateActActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateActBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val firestore = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_act)
        binding = ActivityCreateActBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}