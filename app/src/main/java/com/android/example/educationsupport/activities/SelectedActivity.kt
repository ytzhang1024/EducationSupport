package com.android.example.educationsupport.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.example.educationsupport.R
import com.android.example.educationsupport.databinding.ActivitySelectedBinding
import com.google.firebase.auth.FirebaseAuth

class SelectedActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectedBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected)

        binding = ActivitySelectedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnCourse.setOnClickListener {
            val intent = Intent(this, UnitActivity::class.java)
            startActivity(intent)
        }
    }
}