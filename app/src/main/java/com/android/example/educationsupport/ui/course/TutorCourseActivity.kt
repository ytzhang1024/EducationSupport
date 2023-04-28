package com.android.example.educationsupport.ui.course

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.example.educationsupport.databinding.ActivityCourseBinding
import com.android.example.educationsupport.utils.UiState
import com.android.example.educationsupport.utils.hide
import com.android.example.educationsupport.utils.show
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TutorCourseActivity : AppCompatActivity() {


    val TAG: String = "TutorCourseActivity"
    private lateinit var binding: ActivityCourseBinding
    private val tutorCourseViewModel: TutorCourseViewModel by viewModels()
    val adapter by lazy {
        CourseAdapter(
            onItemClick = { _, Course ->
                val intent = Intent(this, CourseDetailActivity::class.java)
                val courseName = Course.name
                val courseDesc = Course.description

                //Get the clicked course and pass the parameters to the next activity
                intent.putExtra("courseName", courseName)
                intent.putExtra("courseDesc", courseDesc)
                intent.putExtra("role", "Tutor")
                startActivity(intent)
            }
        )
    }

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observer()
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.recyclerCourse.layoutManager = staggeredGridLayoutManager
        binding.recyclerCourse.adapter = adapter

        tutorCourseViewModel.getTutorCourseList()

    }

    private fun observer(){
        tutorCourseViewModel.tutorCourse.observe(this) { state ->
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