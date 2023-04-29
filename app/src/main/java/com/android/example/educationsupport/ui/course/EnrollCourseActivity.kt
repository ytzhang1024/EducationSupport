package com.android.example.educationsupport.ui.course

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.example.educationsupport.databinding.ActivityCourseBinding
import com.android.example.educationsupport.databinding.ActivityEnrollCourseBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EnrollCourseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEnrollCourseBinding
    private val courseViewModel: CourseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnrollCourseBinding.inflate(layoutInflater)
        val courseName = intent.getStringExtra("courseName")
        val courseDesc = intent.getStringExtra("courseDesc")
        setContentView(binding.root)

        binding.courseName.setText(courseName)
        binding.courseDescription.setText(courseDesc)
        binding.btnEnrollModule.setOnClickListener {
            courseViewModel.studentEnrollCourse(courseName)
            finish()
        }

    }
}