package com.android.example.educationsupport.ui.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.example.educationsupport.data.model.Question
import com.android.example.educationsupport.databinding.ActivityQuizDetailBinding
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
class QuizDetailActivity : AppCompatActivity() {

    private lateinit var database : FirebaseFirestore

    val TAG: String = "QuizActivity"
    private var role: String? = null
    private lateinit var binding: ActivityQuizDetailBinding
    private val quizViewModel: QuizViewModel by viewModels()
    //所有question都跳转到question detail，并传值
    val adapter by lazy {

        TutorQuizDetailAdapter(

            onItemClick = { _, Question ->
//                val intent = Intent(this, QuizDetailActivity::class.java)
//                val questionName = Question.title
//                println("---------------------------")
//                println("questionName:"+ questionName)
//                intent.putExtra("questionName", questionName)
//                intent.putExtra("role", role)
//                println("role:"+ role)
//                startActivity(intent)

            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizDetailBinding.inflate(layoutInflater)
        database = FirebaseFirestore.getInstance()
        val questionName = intent.getStringExtra("questionName")

        val activityName = intent.getStringExtra("activityName")
        role = intent.getStringExtra("role")
        binding.btnDeleteQuiz.setOnClickListener{
            if (activityName != null) {

                if (questionName != null) {
                    quizViewModel.deleteQuestion(activityName,questionName)
                }
                Toast.makeText(this, "Delete Successful", Toast.LENGTH_SHORT).show()
            }
        }
        setContentView(binding.root)
        observer()
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        binding.recyclerActivity.layoutManager = staggeredGridLayoutManager
        binding.recyclerActivity.adapter = adapter


        if (questionName != null) {

            quizViewModel.tutorGetQuestionDetail(questionName!!)

        }





    }
    private fun observer(){
        quizViewModel.tutorQuestionDetail.observe(this) { state ->
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