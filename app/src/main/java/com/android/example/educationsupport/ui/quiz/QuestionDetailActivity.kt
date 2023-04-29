package com.android.example.educationsupport.ui.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.android.example.educationsupport.databinding.ActivityQuestionDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionDetailActivity : AppCompatActivity() {
    val TAG: String = "QuestionDetailActivity"
    private var role: String? = null
    private lateinit var binding: ActivityQuestionDetailBinding
    private val viewModel: QuizViewModel by viewModels()
    val adapter by lazy {
        QuestionDetalAdapter(

        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}