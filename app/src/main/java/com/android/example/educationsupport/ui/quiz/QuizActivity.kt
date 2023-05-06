package com.android.example.educationsupport.ui.quiz

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.example.educationsupport.data.model.Question
import com.android.example.educationsupport.databinding.ActivityQuizBinding
import com.android.example.educationsupport.ui.base.SignInActivity
import com.android.example.educationsupport.ui.start.activity.QuizDescActivity
import com.android.example.educationsupport.utils.UiState
import com.android.example.educationsupport.utils.hide
import com.android.example.educationsupport.utils.show
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class QuizActivity : AppCompatActivity() {



    val TAG: String = "QuizActivity"
    private lateinit var database : FirebaseFirestore
    private var role: String? = null
    private var activityName: String? = null
    private lateinit var binding: ActivityQuizBinding
    private val quizViewModel: QuizViewModel by viewModels()
    private var questionList: List<Question> ? = null
    //所有question都跳转到question detail，并传值

    val adapter by lazy {
        QuizAdapter(
            onItemClick = { _, Question ->
                if (!role.equals("Student")) {
                    val intent = Intent(this, QuizDetailActivity::class.java)
                    val questionName = Question.title
                    println("---------------------------")
                    println("questionName:"+ questionName)
                    intent.putExtra("questionName", questionName)

                    intent.putExtra("role", role)
                    intent.putExtra("activityName", activityName)
                    println("-++++++++++++++++++++++++")
                    println("role:"+ role)
                    println("QuizActivity activityName:"+ activityName)
                    startActivity(intent)
                }

            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        database = FirebaseFirestore.getInstance()

        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        activityName = intent.getStringExtra("activityName")
        role = intent.getStringExtra("role")
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

                val intent = Intent(this, StudentQuizDetailActivity::class.java)
                intent.putExtra("questionName", "Detailtest")
                val questionArrayList = ArrayList(questionList)
                intent.putExtra("questionList", questionArrayList)
                intent.putExtra("role", role)
                intent.putExtra("activityName", activityName)
                startActivity(intent)
            }
        }
        setContentView(binding.root)

        observer()
    //    activityName?.let { getQuestionList(it) }

        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.recyclerQuestion.layoutManager = staggeredGridLayoutManager
        binding.recyclerQuestion.adapter = adapter

        if (activityName != null) {
            quizViewModel.getQuestionList(activityName!!)
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
                    questionList = state.data
                    adapter.updateList(questionList!!.toMutableList())
                    println("------------get question list (list type):"+ questionList!!.size)
                }
            }
        }
    }

    //用于Start Quiz跳转
//    fun getQuestionList(activityName: String)  {
//        val docRef = database.collection("activityQuestionMapping").document(activityName)
//
//        docRef.get().addOnSuccessListener { documentSnapshot ->
//            //get question id
//            val data = documentSnapshot.get("question")
//
//            data as List<String>
//            println("$$$$$$$$$$$$$$$$$$:"+data[0])
//            val intent = Intent(this, QuizDetailActivity::class.java)
//            intent.putExtra("activityName", activityName)
//            intent.putExtra("questionName", data[0])
//            intent.putExtra("role", role)
//            startActivity(intent)
//
//
//        }
//    }


}