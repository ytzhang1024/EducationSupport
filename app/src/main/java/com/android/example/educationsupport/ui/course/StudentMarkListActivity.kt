package com.android.example.educationsupport.ui.course

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.android.example.educationsupport.R
import com.android.example.educationsupport.databinding.ActivityCourseResultBinding
import com.android.example.educationsupport.databinding.ActivityCourseBinding
import com.android.example.educationsupport.databinding.ActivityStudentMarkListBinding



class StudentMarkListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentMarkListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_mark_list)
        binding = ActivityStudentMarkListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnCourseResult = findViewById<Button>(R.id.btn_student_list)
        btnCourseResult.setOnClickListener {
            val intent = Intent(this, CourseResultActivity::class.java)
            startActivity(intent)
        }
    }
}