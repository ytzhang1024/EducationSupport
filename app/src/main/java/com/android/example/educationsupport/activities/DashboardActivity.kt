package com.android.example.educationsupport.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.android.example.educationsupport.R
import com.android.example.educationsupport.databinding.ActivityDashboardBinding


class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Jump to ViewUserActivity
//        val btnProfile = findViewById<ImageButton>(R.id.profile)
//        btnProfile.setOnClickListener {
//            print("VVVVVVVVVVVVVVV")
//            val intent = Intent(this, ViewUserActivity::class.java)
//            startActivity(intent)
//        }

    }
}