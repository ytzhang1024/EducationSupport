package com.android.example.educationsupport.ui.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.example.educationsupport.databinding.ActivityQuizBinding
import com.android.example.educationsupport.ui.start.Fragment.QuizDetailActivity
import com.android.example.educationsupport.ui.start.activity.QuizDescActivity
import com.android.example.educationsupport.utils.UiState
import com.android.example.educationsupport.utils.hide
import com.android.example.educationsupport.utils.show
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class QuizActivity : AppCompatActivity() {

    val TAG: String = "QuizActivity"
    private lateinit var binding: ActivityQuizBinding
    private val quizViewModel: QuizViewModel by viewModels()
    val adapter by lazy {
        QuizAdapter(
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        val activityName = intent.getStringExtra("activityName")
        val role = intent.getStringExtra("role")
        binding.activityName.setText(activityName)
        if (!role.equals("Student")) {
            binding.btnCreateQuesion.show()
            binding.btnCreateQuesion.setOnClickListener{
                val intent = Intent(this, CreateQuestionActivity::class.java)
                intent.putExtra("activityName", activityName)
                startActivity(intent)
            }
        } else {
            binding.btnStartQuiz.show()
            println(activityName)
            binding.btnStartQuiz.setOnClickListener{
                val intent = Intent(this, QuizDescActivity::class.java)
                intent.putExtra("activityName", activityName)
                startActivity(intent)
            }
        }
        setContentView(binding.root)

        observer()
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.recyclerQuestion.layoutManager = staggeredGridLayoutManager
        binding.recyclerQuestion.adapter = adapter

        if (activityName != null) {
            quizViewModel.getQuestionList(activityName)
        }
    }


    private fun observer(){
        quizViewModel.allQuestion.observe(this) { state ->
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