package com.android.example.educationsupport.ui.course

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.android.example.educationsupport.R
import com.android.example.educationsupport.databinding.ActivityCourseResultBinding

class CourseResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCourseResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_courses)
        binding = ActivityCourseResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnCourseResult = findViewById<Button>(R.id.btn_course)
        btnCourseResult.setOnClickListener {
            val intent = Intent(this, ResultActivity::class.java)
            startActivity(intent)
        }
    }
}