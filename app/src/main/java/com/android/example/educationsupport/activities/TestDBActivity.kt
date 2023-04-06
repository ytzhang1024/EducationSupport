package com.android.example.educationsupport.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.example.educationsupport.databinding.ActivityTestDbactivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TestDBActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTestDbactivityBinding
    private val firestore = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTestDbactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val course = binding.courseET.text.toString()
            val courseMap = hashMapOf(
                "name" to course
            )
            val userId = FirebaseAuth.getInstance().currentUser!!.uid
            firestore.collection("course").document(userId).set(courseMap).addOnSuccessListener {
                Toast.makeText(this, "Adding Successful", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this, "Adding Failure", Toast.LENGTH_SHORT).show()
            }
        }
    }
}