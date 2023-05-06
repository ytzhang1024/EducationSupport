package com.android.example.educationsupport.ui.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.example.educationsupport.databinding.ActivityStudentQuizDetailBinding
import com.android.example.educationsupport.utils.UiState
import com.android.example.educationsupport.utils.hide
import com.android.example.educationsupport.utils.show
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import com.android.example.educationsupport.data.model.Question
import com.android.example.educationsupport.data.model.QuizRecord
import com.android.example.educationsupport.ui.course.ResultActivity


@AndroidEntryPoint
class StudentQuizDetailActivity : AppCompatActivity() {

    private lateinit var database: FirebaseFirestore
    private lateinit var binding: ActivityStudentQuizDetailBinding
    private val quizViewModel: QuizViewModel by viewModels()
    private val adapter by lazy {
        QuizDetailAdapter(
            onItemClick = { _, question -> }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentQuizDetailBinding.inflate(layoutInflater)
        database = FirebaseFirestore.getInstance()

        val activityName = intent.getStringExtra("activityName")
        val questionArrayList = intent.getParcelableArrayListExtra<Question>("questionList")
        val questionList = questionArrayList?.toList()

        setContentView(binding.root)

        observer()

        val staggeredGridLayoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        binding.recyclerActivity.layoutManager = staggeredGridLayoutManager
        binding.recyclerActivity.adapter = adapter

        var j = 1
        var score = 0
        val answerMap = mutableMapOf<String, String>()
        if (questionList != null) {
            quizViewModel.studentGetQuestionDetail(questionList[0].title!!)
        }

        binding.btnSubmitQuiz.setOnClickListener {

            val selectedOptions = mutableListOf<String>()
            for (i in 0 until adapter.itemCount) {
                val viewHolder = binding.recyclerActivity.findViewHolderForAdapterPosition(i) as? QuizDetailAdapter.MyViewHolder
                viewHolder?.let {
                    val options = it.getSelectedOptions() //get selected answer
                    options?.let { selectedOptions.addAll(it) }
                    it.setCheckBox()// reset checkbox
                    score = it.calculate(score, selectedOptions.toString())!! // calculate scores
                }
            }

            if (j < questionList!!.size) {
                val question = questionList[j] //翻页，因为第一次按下按钮是跳到第二页，所以j = 1
                val questionTitle = questionList[j-1]//获取题目名字，因为第一次按下按钮获取第一页（第一题），所以j = 0
                answerMap[questionTitle.title!!] = selectedOptions.toString()
                j++
                quizViewModel.studentGetQuestionDetail(question.title!!)
                val quizRecord = QuizRecord(
                    activity_name = activityName,
                    answer = answerMap,
                    mark = score
                )
                quizViewModel.addQuizRecord(quizRecord)
            } else {

                val questionTitle = questionList[j-1]//获取题目名字，因为第一次按下按钮获取第一页（第一题），所以j = 0
                answerMap[questionTitle.title!!] = selectedOptions.toString()
                val quizRecord = QuizRecord(
                    activity_name = activityName,
                    answer = answerMap,
                    mark = score
                )
                quizViewModel.addQuizRecord(quizRecord)

                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("overallScore", questionList.size)
                intent.putExtra("score",score )
                println("------------score in studentquizdetail:"+score)
                startActivity(intent)

            }
        }


    }

    private fun observer() {
        quizViewModel.studentQuestionDetail.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBar.show()
                }
                is UiState.Failure -> {
                    binding.progressBar.hide()
                    Toast.makeText(this, state.error, Toast.LENGTH_LONG).show()
                }
                is UiState.Success -> {
                    binding.progressBar.hide()
                    adapter.updateList(state.data.toMutableList())
                }
            }
        }
    }
}
