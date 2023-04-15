package com.android.example.educationsupport.ui.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.example.educationsupport.R
import com.android.example.educationsupport.databinding.ActivityUserProfileBinding
import com.android.example.educationsupport.repository.entity.User
import com.android.example.educationsupport.repository.firebase.FirebaseRepository
import com.android.example.educationsupport.ui.base.SignInActivity
import com.android.example.educationsupport.viewModel.UserProfileViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


open class UserProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel = ViewModelProvider(this)[UserProfileViewModel::class.java]
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        viewModel.getUserProfile()
//        viewModel.test()
//        val user = viewModel.getUser()
//        println("test")
//        println(viewModel.getRes())

//        binding.etFirstName.setText("FirstName")
//        binding.etLastName.setText("LastName")
//        binding.etBio.setText("bio")


        binding.saveBtn.setOnClickListener {
            viewModel.SaveUserProfile(binding)
        }


        }

}