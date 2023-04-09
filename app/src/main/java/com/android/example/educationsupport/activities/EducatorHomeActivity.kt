package com.android.example.educationsupport.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.example.educationsupport.R
import com.android.example.educationsupport.databinding.ActivityEducatorHomeBinding

class EducatorHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEducatorHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_educator_home)
        binding = ActivityEducatorHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Jump to AllCoursesActivity
        binding.allBtn.setOnClickListener {
            val intent = Intent(this, AllCoursesActivity::class.java)
            startActivity(intent)
        }


    }
}