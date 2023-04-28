package com.android.example.educationsupport.ui.quiz

import android.R
import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.android.example.educationsupport.data.model.Question
import com.android.example.educationsupport.databinding.ActivityCreateQuestionBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateQuestionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateQuestionBinding
    private val createQuestionViewModel: CreateQuestionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateQuestionBinding.inflate(layoutInflater)
        val activityName = intent.getStringExtra("activityName")
        setContentView(binding.root)

        binding.btnSubmitQuestion.setOnClickListener {
            var question = Question(
                title = binding.questionTitle.text.toString(),
                option_A = binding.optionA.text.toString(),
                option_B = binding.optionB.text.toString(),
                option_C = binding.optionC.text.toString(),
                option_D = binding.optionD.text.toString(),
                correct_answer = checkWhichCheckIsTicked()
                )
            if (activityName != null) {
                createQuestionViewModel.addQuestion(activityName, question)
            }

            Toast.makeText(this, "Create Successful", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    fun checkWhichCheckIsTicked(): List<String>? {
        var res: List<String>? = null
        val checkBoxA = binding.checkBoxA
        val checkBoxB = binding.checkBoxB
        val checkBoxC = binding.checkBoxC
        val checkBoxD = binding.checkBoxD

        if (checkBoxA.isChecked) {
            res = res.orEmpty().plus("A")
        }
        if (checkBoxB.isChecked) {
            res = res.orEmpty().plus("B")
        }
        if (checkBoxC.isChecked) {
           res = res.orEmpty().plus("C")
        }
        if (checkBoxD.isChecked) {
            res = res.orEmpty().plus("D")
        }

        return res
    }
}