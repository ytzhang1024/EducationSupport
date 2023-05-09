package com.android.example.educationsupport.ui.course

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.example.educationsupport.databinding.ActivityCourseBinding
import com.android.example.educationsupport.utils.UiState
import com.android.example.educationsupport.utils.hide
import com.android.example.educationsupport.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllCoursesActivity : AppCompatActivity() {

    val TAG: String = "AllCourseActivity"
    private lateinit var binding: ActivityCourseBinding
    private val allCourseViewModel: AllCourseViewModel by viewModels()
    val adapter by lazy {
        CourseAdapter(
            onItemClick = { _, Course ->
                val intent = Intent(this, EnrollCourseActivity::class.java)
                val courseName = Course.name
                val courseDesc = Course.description
                val couseEmail = Course.tutorEmail
                intent.putExtra("courseName", courseName)
                intent.putExtra("courseDesc", courseDesc)
                intent.putExtra("couseEmail", couseEmail)
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


        allCourseViewModel.getCourseList()
        observer()
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        binding.recyclerCourse.layoutManager = staggeredGridLayoutManager
        binding.recyclerCourse.adapter = adapter


    }

    private fun observer(){
        allCourseViewModel.allCourse.observe(this) { state ->
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