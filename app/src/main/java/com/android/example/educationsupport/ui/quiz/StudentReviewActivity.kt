package com.android.example.educationsupport.ui.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.example.educationsupport.data.model.Question
import com.android.example.educationsupport.databinding.ActivityStudentReviewBinding
import com.android.example.educationsupport.ui.base.SignInActivity
import com.android.example.educationsupport.ui.start.activity.QuizDescActivity
import com.android.example.educationsupport.ui.quiz.QuizDetailAdapter
import com.android.example.educationsupport.utils.UiState
import com.android.example.educationsupport.utils.hide
import com.android.example.educationsupport.utils.show
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class StudentReviewActivity : AppCompatActivity() {

    private lateinit var database : FirebaseFirestore

    val TAG: String = "QuizActivity"
    private var role: String? = null
    private lateinit var binding: ActivityStudentReviewBinding
    private val quizViewModel: QuizViewModel by viewModels()

    private val adapter by lazy {
        StudentReviewlAdapter(
            onItemClick = { _, quizRecord -> }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentReviewBinding.inflate(layoutInflater)
        database = FirebaseFirestore.getInstance()
        var studentEmail = intent.getStringExtra("studentEmail")
        val activityName = intent.getStringExtra("activityName")
        val questionArrayList = intent.getParcelableArrayListExtra<Question>("questionList")
        val questionList = questionArrayList?.toList()

        setContentView(binding.root)
        observer()
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        binding.recyclerActivity.layoutManager = staggeredGridLayoutManager
        binding.recyclerActivity.adapter = adapter
//        println("--------------------test studentEmail:"+ studentEmail)
//        println("--------------------test activityName:"+ activityName)

        if(studentEmail == null){
            studentEmail = "studentEmail"
        }

        if (activityName != null) {
            println("--------------------test studentEmail:"+ studentEmail)
            quizViewModel.studentGetReview(studentEmail,activityName)
        }






    }
    private fun observer(){
        quizViewModel.stuentReview.observe(this) { state ->
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