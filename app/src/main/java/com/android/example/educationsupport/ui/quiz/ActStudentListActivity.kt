package com.android.example.educationsupport.ui.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.example.educationsupport.databinding.ActivityActStudentListBinding
import com.android.example.educationsupport.databinding.ActivityCourseBinding
import com.android.example.educationsupport.utils.UiState
import com.android.example.educationsupport.utils.hide
import com.android.example.educationsupport.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActStudentListActivity : AppCompatActivity() {

    val TAG: String = "AllCourseActivity"
    private lateinit var binding: ActivityActStudentListBinding
    private var activityName: String? = null
    private val quizViewModel: QuizViewModel by viewModels()


    val adapter by lazy {
        StudentListAdapter(
            onItemClick = { _, User ->
                val intent = Intent(this, StudentReviewActivity::class.java)
                intent.putExtra("activityName", activityName)
                println("--------------------test activityName student list pass to :"+ activityName)
                val studentEmail = User.email
                intent.putExtra("studentEmail", studentEmail)
                startActivity(intent)
            }
        )
    }

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        binding = ActivityActStudentListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activityName = intent.getStringExtra("activityName")
        println("--------------------test activityName in student list:"+ activityName)
        observer()
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        binding.recyclerCourse.layoutManager = staggeredGridLayoutManager
        binding.recyclerCourse.adapter = adapter

        if (activityName != null) {
            quizViewModel.getStudentList(activityName!!)
        }


    }

    private fun observer(){
        //  val getStudentList: LiveData<UiState<List<QuizRecord>>>
        quizViewModel.getStudentList.observe(this) { state ->
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