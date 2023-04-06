package com.android.example.educationsupport.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.android.example.educationsupport.R
import com.android.example.educationsupport.databinding.ActivityTestDbactivityBinding
import com.android.example.educationsupport.databinding.ActivityUserProfileBinding
import com.android.example.educationsupport.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserProfileBinding
    private val firestore = Firebase.firestore
    private lateinit var firebaseAuth: FirebaseAuth
//    private lateinit var image: ImageView
//    private lateinit var btnBrowse: Button
    private val uemail = firebaseAuth.currentUser?.email // TODO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
//        image = findViewById(R.id.profile_image)
//        btnBrowse = findViewById(R.id.upload_browse)

        binding.saveBtn.setOnClickListener{

            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val bio = binding.etBio.text.toString()

            val usermap = hashMapOf(
                "firstName" to firstName,
                "lastName" to lastName,
                "bio" to bio
            )

//            val user = User(firstName, lastName, bio)
            if (uemail != null) {
                firestore.collection("user").document(uemail).update(usermap as Map<String, Any>).addOnSuccessListener {
                    Toast.makeText(this@UserProfileActivity, "Adding Profile Successful", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener{
                    Toast.makeText(this@UserProfileActivity, "Adding Profile Failure", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }
}