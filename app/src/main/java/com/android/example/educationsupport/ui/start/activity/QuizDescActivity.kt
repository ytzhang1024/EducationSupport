package com.android.example.educationsupport.ui.start.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.android.example.educationsupport.databinding.ActivityQuizDescBinding
import com.android.example.educationsupport.utils.show
import dagger.hilt.android.AndroidEntryPoint


//如果学生没参加过，可以参加，如果已经参加过，则显示成绩按钮


@AndroidEntryPoint
class QuizDescActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuizDescBinding
    private val viewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizDescBinding.inflate(layoutInflater)
        var activityName = intent.getStringExtra("activityName")

        if (activityName != null) {
            observer(activityName)
        }


        binding.startBtn.setOnClickListener {
            val intent = Intent(this, QuizStartingActivity::class.java)
            intent.putExtra("activityName", activityName)
            startActivity(intent)
        }

        //If student finish task, then go to the Result Page
//        binding.resultBtn.setOnClickListener {
//            val intent = Intent(this, XXXXXXX::class.java)
//            startActivity(intent)
//        }

        setContentView(binding.root)
    }

    private fun observer(activityName: String){
        viewModel.checkStudentIfFinishTest(activityName)
        viewModel.isFinish.observe(this) { state ->
            when (state) {
                false -> binding.startBtn.show()
                else -> {
                    binding.resultBtn.show()
                }
            }
        }
    }
}