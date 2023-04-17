package com.android.example.educationsupport.ui.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.android.example.educationsupport.R
import com.android.example.educationsupport.databinding.ActivityViewUserBinding
import com.android.example.educationsupport.ui.activities.MainActivity
import com.android.example.educationsupport.ui.course.CourseResultActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ViewUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewUserBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var u_email: String
    private lateinit var user: User
    private val firestore = Firebase.firestore
    private lateinit var user_data: User


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnBackToDashboard = findViewById<Button>(R.id.back)
        btnBackToDashboard.setOnClickListener {
            finish()
        }

        auth = FirebaseAuth.getInstance()
        u_email = auth.currentUser?.email.toString()
        val ref = firestore.collection("user").document(u_email)
        ref.get().addOnSuccessListener {
            if (it != null) {
                val full_name = it.data?.get("firstName").toString() + " " + it.data?.get("lastName").toString()
                val bio = it.data?.get("bio").toString()
                val role  = it.data?.get("role").toString()

                binding.fullName.setText(full_name)
                binding.tvEmail.setText(u_email)
                binding.userBio.setText(bio)
                binding.userRole.setText(role)
            }
        }
            .addOnFailureListener {
                Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
            }
    }
}