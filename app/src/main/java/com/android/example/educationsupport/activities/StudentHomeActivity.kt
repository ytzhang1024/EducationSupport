package com.android.example.educationsupport.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.example.educationsupport.R
import com.android.example.educationsupport.databinding.ActivityStudentHomeBinding

class StudentHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_home)
        binding = ActivityStudentHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Jump to AllCoursesActivity
        binding.allBtn2.setOnClickListener {
            val intent = Intent(this, AllCoursesActivity::class.java)
            startActivity(intent)
        }
    }
}