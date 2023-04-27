package com.android.example.educationsupport.ui.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.android.example.educationsupport.databinding.ActivityUserProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


open class UserProfileActivity : AppCompatActivity() {
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore = Firebase.firestore
    private val uemail = firebaseAuth?.currentUser?.email.toString()
    private lateinit var binding: ActivityUserProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel = ViewModelProvider(this)[UserProfileViewModel::class.java]
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uemail = firebaseAuth?.currentUser?.email.toString()
        val ref = firestore.collection("user").document(uemail)
        ref.get().addOnSuccessListener {
            binding.etFirstName.setText(it.data?.get("firstName").toString())
            binding.etLastName.setText(it.data?.get("lastName").toString())
            binding.etBio.setText(it.data?.get("bio").toString() )
        }

        binding.saveBtn.setOnClickListener {
            viewModel.SaveUserProfile(binding)
            finish()
        }
    }

}