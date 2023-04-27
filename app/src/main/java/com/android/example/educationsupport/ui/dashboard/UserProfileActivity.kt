package com.android.example.educationsupport.ui.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.android.example.educationsupport.databinding.ActivityUserProfileBinding
import com.android.example.educationsupport.data.repository.FirebaseRepository


open class UserProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserProfileBinding
    private val firebaseRepository = FirebaseRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel = ViewModelProvider(this)[UserProfileViewModel::class.java]
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uemail = firebaseRepository.getFirebaseAuth()?.currentUser?.email.toString()
        val ref = firebaseRepository.getFireStore().collection("user").document(uemail)
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