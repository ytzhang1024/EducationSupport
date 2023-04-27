package com.android.example.educationsupport.ui.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.example.educationsupport.R
import com.android.example.educationsupport.databinding.ActivityCourseBinding

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCourseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course)
        binding = ActivityCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.btnCourse.setOnClickListener {
//            val intent = Intent(this, QuestionActivity::class.java)
//            startActivity(intent)
//        }
    }
}