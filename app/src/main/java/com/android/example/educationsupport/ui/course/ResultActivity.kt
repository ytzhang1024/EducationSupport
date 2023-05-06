package com.android.example.educationsupport.ui.course

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.android.example.educationsupport.R

import com.android.example.educationsupport.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_courses)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val overallScore = intent.getIntExtra("overallScore",100)
        val score = intent.getIntExtra("score",100)
        binding.txtCorrectAns.setText(score.toString() +"/"+overallScore.toString())
        binding.tvPerc.setText(score.toString() )

    }
}