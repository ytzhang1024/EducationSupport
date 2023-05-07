package com.android.example.educationsupport.ui.course

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.example.educationsupport.R
import com.android.example.educationsupport.databinding.ActivityCourseResultBinding
import com.android.example.educationsupport.ui.Activity.CourseResultAdapter
import com.android.example.educationsupport.ui.course.CourseResultViewModel
import com.android.example.educationsupport.ui.quiz.StudentListAdapter
import com.android.example.educationsupport.ui.quiz.StudentReviewActivity
import com.android.example.educationsupport.utils.UiState
import com.android.example.educationsupport.utils.hide
import com.android.example.educationsupport.utils.show
import dagger.hilt.android.AndroidEntryPoint

// From home page
@AndroidEntryPoint
class CourseResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCourseResultBinding
    private val courseResultViewModel: CourseResultViewModel by viewModels()

    val adapter by lazy {
        CourseResultAdapter(
            onItemClick = { _, Activity ->
                val intent = Intent(this, StudentReviewActivity::class.java)
                val activityName = Activity.title
                intent.putExtra("activityName", activityName)
                startActivity(intent)
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_courses)
        binding = ActivityCourseResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observer()
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        binding.recyclerCourse.layoutManager = staggeredGridLayoutManager
        binding.recyclerCourse.adapter = adapter

        courseResultViewModel.getCompletedActivitList()



    }
    private fun observer(){
        //  val getStudentList: LiveData<UiState<List<QuizRecord>>>
        courseResultViewModel.completedActivityList.observe(this) { state ->
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