package com.android.example.educationsupport.ui.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.example.educationsupport.R
import com.android.example.educationsupport.data.model.Activity
import com.android.example.educationsupport.data.model.Course
import com.android.example.educationsupport.databinding.ActivityCreateActBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateActActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateActBinding
    private val createActivityViewModel: CreateActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_act)
        binding = ActivityCreateActBinding.inflate(layoutInflater)
        val courseName = intent.getStringExtra("courseName")
        setContentView(binding.root)

        binding.btnSubmitActivity.setOnClickListener {
            var activity = Activity(
                title = binding.activityTitleET.text.toString(),
                course = courseName
            )
            createActivityViewModel.addActivity(activity)

            Toast.makeText(this, "Create Successful", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

}