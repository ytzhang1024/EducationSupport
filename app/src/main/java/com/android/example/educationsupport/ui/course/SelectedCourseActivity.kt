package com.android.example.educationsupport.ui.course

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.example.educationsupport.databinding.ActivitySelectedCourseBinding
import com.android.example.educationsupport.utils.UiState
import com.android.example.educationsupport.utils.hide
import com.android.example.educationsupport.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectedCourseActivity : AppCompatActivity() {

    val TAG: String = "StudentCourseActivity"
    private lateinit var binding: ActivitySelectedCourseBinding
    private val SelectedCourseViewModel: SelectedCourseViewModel by viewModels()
    val adapter by lazy {
        CourseAdapter(
        )
    }

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectedCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observer()
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.recyclerCourse.layoutManager = staggeredGridLayoutManager
        binding.recyclerCourse.adapter = adapter

        SelectedCourseViewModel.getStudentCourseList()


        //Go to course detail page
        binding.recyclerCourse.setOnClickListener {

        }

    }

    private fun observer(){
        SelectedCourseViewModel.studentCourse.observe(this) { state ->
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