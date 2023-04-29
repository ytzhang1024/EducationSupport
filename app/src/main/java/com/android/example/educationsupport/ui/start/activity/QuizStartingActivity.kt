package com.android.example.educationsupport.ui.start.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.android.example.educationsupport.databinding.ActivityQuizDescBinding
import com.android.example.educationsupport.databinding.ActivityQuizStartingBinding
import com.android.example.educationsupport.utils.show
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class QuizStartingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizStartingBinding
    private val viewModel: QuizViewModel by viewModels()
    private var activityName = intent.getStringExtra("activityName")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizStartingBinding.inflate(layoutInflater)

    }

}