package com.android.example.educationsupport.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.example.educationsupport.databinding.ActivityTestDbactivityBinding
import com.android.example.educationsupport.databinding.ActivityViewUserBinding
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

        auth = FirebaseAuth.getInstance()
        u_email = auth.currentUser?.email.toString()
        val ref = firestore.collection("user").document(u_email)
        ref.get().addOnSuccessListener {
            if (it != null) {
                val full_name = it.data?.get("lastName").toString() + " " + it.data?.get("firstName").toString()
                val bio = it.data?.get("bio").toString()
                val role  = it.data?.get("role").toString()

                binding.fullName.setText(full_name)
                binding.userEmail.setText(u_email)
                binding.userBio.setText(bio)
            }
        }
            .addOnFailureListener {
                Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
            }
    }
}