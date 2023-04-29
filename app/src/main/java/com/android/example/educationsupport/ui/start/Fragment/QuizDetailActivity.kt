package com.android.example.educationsupport.ui.start.Fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.example.educationsupport.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class QuizDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_detail)

    }
}