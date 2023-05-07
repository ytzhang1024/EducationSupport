package com.android.example.educationsupport.ui.course

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.android.example.educationsupport.R
import com.android.example.educationsupport.data.model.Question

import com.android.example.educationsupport.databinding.ActivityResultBinding
import com.android.example.educationsupport.ui.quiz.StudentReviewActivity
import com.android.example.educationsupport.utils.UiState
import com.android.example.educationsupport.utils.hide
import com.android.example.educationsupport.utils.show
//Only score
class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_courses)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val overallScore = intent.getIntExtra("overallScore",100)
        val score = intent.getIntExtra("score",100)
        val questionArrayList = intent.getParcelableArrayListExtra<Question>("questionList")
        val activityName = intent.getStringExtra("activityName")
        binding.txtCorrectAns.setText(score.toString() +"/"+overallScore.toString())
        binding.tvPerc.setText(score.toString() )

        binding.btnShowReview.setOnClickListener {
            val intent = Intent(this, StudentReviewActivity::class.java)
            intent.putExtra("questionList", questionArrayList)
            intent.putExtra("activityName", activityName)

//        intent.putExtra("overallScore", questionList.size)
//        intent.putExtra("score",score )
//        println("------------score in studentquizdetail:"+score)
            startActivity(intent)
        }




    }

}