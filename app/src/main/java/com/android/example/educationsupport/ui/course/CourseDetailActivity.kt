package com.android.example.educationsupport.ui.course

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.example.educationsupport.databinding.ActivityCourseBinding
import com.android.example.educationsupport.databinding.ActivityCourseDetailBinding
import com.android.example.educationsupport.ui.Activity.ActivityAdapter
import com.android.example.educationsupport.ui.quiz.CreateActActivity
import com.android.example.educationsupport.ui.quiz.QuizActivity
import com.android.example.educationsupport.utils.UiState
import com.android.example.educationsupport.utils.hide
import com.android.example.educationsupport.utils.show
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CourseDetailActivity : AppCompatActivity() {
    val TAG: String = "CourseDetailActivity"
    private var role: String? = null
    private lateinit var binding: ActivityCourseDetailBinding
    private val courseDetailViewModel: CourseDetailViewModel by viewModels()
    val adapter by lazy {
        ActivityAdapter(
            onItemClick = { _, Activity ->
                val intent = Intent(this, QuizActivity::class.java)
                val activityName = Activity.title
                intent.putExtra("activityName", activityName)
                intent.putExtra("role", role)
                startActivity(intent)
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseDetailBinding.inflate(layoutInflater)
        val courseName = intent.getStringExtra("courseName")
        val courseDesc = intent.getStringExtra("courseDesc")
        role = intent.getStringExtra("role")
        binding.courseName.setText(courseName)
        binding.courseDescription.setText(courseDesc)
        if (!role.equals("Student")) {
            binding.btnCreateActivity.show()
            binding.btnCreateActivity.setOnClickListener{
                val intent = Intent(this, CreateActActivity::class.java)
                intent.putExtra("courseName", courseName)
                startActivity(intent)
            }
        }
        setContentView(binding.root)

        observer()
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.recyclerActivity.layoutManager = staggeredGridLayoutManager
        binding.recyclerActivity.adapter = adapter

        if (courseName != null) {
            courseDetailViewModel.getCourseActivityList(courseName)
        }
    }


    private fun observer(){
        courseDetailViewModel.courseActivity.observe(this) { state ->
            when(state){
                is UiState.Loading -> {
                    binding.progressBar.show()
                    println("loading")

                }
                is UiState.Failure -> {
                    binding.progressBar.hide()
                    Toast.makeText(this,state.error, Toast.LENGTH_LONG).show()
                    println("Fail")

                }
                is UiState.Success -> {
                    binding.progressBar.hide()
                    adapter.updateList(state.data.toMutableList())
                }
            }
        }
    }

}