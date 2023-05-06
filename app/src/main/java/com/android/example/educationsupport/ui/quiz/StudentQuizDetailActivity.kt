package com.android.example.educationsupport.ui.quiz

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


@AndroidEntryPoint
class StudentQuizDetailActivity : AppCompatActivity() {

    private lateinit var database : FirebaseFirestore

    val TAG: String = "QuizActivity"
    private var role: String? = null
    private lateinit var binding: ActivityStudentQuizDetailBinding
    private val quizViewModel: QuizViewModel by viewModels()
    //所有question都跳转到question detail，并传值
    val adapter by lazy {
        println("---------------------------sutdent adapter")
        QuizDetailAdapter(

            onItemClick = { _, Question ->
//                binding.btnSubmitQuiz.setOnClickListener {
//                    val selectedOptions = mutableListOf<String>()
//                    for (i in 0 until adapter.itemCount) {
//                        val viewHolder = binding.recyclerActivity.findViewHolderForAdapterPosition(i) as? QuizDetailAdapter.MyViewHolder
//                        viewHolder?.let {
//                            val options = it.getSelectedOptions()
//                            options?.let { selectedOptions.addAll(it) }
//                        }
//                        println(i)
//                    }
//
//
//                    println("Selected Options: $selectedOptions")
//                    // do something with selected options
//                }
//

            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentQuizDetailBinding.inflate(layoutInflater)
        database = FirebaseFirestore.getInstance()
       // val questionName = intent.getStringExtra("questionName")

        val activityName = intent.getStringExtra("activityName")
        val questionArrayList = intent.getParcelableArrayListExtra<Question>("questionList")

        // convert the ArrayList back to a List
        val questionList = questionArrayList?.toList()

        //questionList.get(0): Question(title=Keytest, option_A=1, option_B=2, option_C=3, option_D=4, correct_answer=A,B)

        role = intent.getStringExtra("role")

        setContentView(binding.root)


        observer()

        val staggeredGridLayoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        binding.recyclerActivity.layoutManager = staggeredGridLayoutManager
        binding.recyclerActivity.adapter = adapter

        val selectedOptions = mutableListOf<String>()


        if (questionList != null) {
                quizViewModel.studentGetQuestionDetail(questionList.get(0).title!!)

        }
        var j = 1
        var questionName: String? = null
        val answerMap = mutableMapOf<String, String>()
        binding.btnSubmitQuiz.setOnClickListener {


            val selectedOptions = mutableListOf<String>()
            for (i in 0 until adapter.itemCount) {
                if (questionList != null) {
                    quizViewModel.studentGetQuestionDetail(questionList.get(j).title!!)
                    questionName = questionList.get(j-1).title!!

                    j = j + 1
                }
                val viewHolder = binding.recyclerActivity.findViewHolderForAdapterPosition(i) as? QuizDetailAdapter.MyViewHolder
                viewHolder?.let {
                    val options = it.getSelectedOptions()
                    options?.let { selectedOptions.addAll(it) }
                    it.setCheckBox()
                }
                if (questionList != null) {

                }
            }
            println("questionList.get(j).title!!:"+ questionName)
            println("Selected Options: $selectedOptions")

            questionName?.let {
                answerMap[it] = selectedOptions.toString()
            }

            var questionRecord = QuizRecord(
                activity_name = activityName,
                answer = answerMap

            )
            quizViewModel.addQuizRecord(questionRecord)
            println("------------test answerMap:"+answerMap)
            println("------------test questionRecord:"+questionRecord)

        }



    }

    private fun observer(){
        quizViewModel.studentQuestionDetail.observe(this) { state ->
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
    private fun getViewHolderForPosition(position: Int): QuizDetailAdapter.MyViewHolder? {
        return binding.recyclerActivity.findViewHolderForAdapterPosition(position) as? QuizDetailAdapter.MyViewHolder
    }



}