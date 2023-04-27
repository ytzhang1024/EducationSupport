package com.android.example.educationsupport.ui.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.android.example.educationsupport.R
import com.android.example.educationsupport.databinding.ActivityCreateModuleBinding

class CreateModuleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateModuleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_module)
        binding = ActivityCreateModuleBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        Jump to create course
        val btnCreateCourse = findViewById<Button>(R.id.btnCreateCourse)
        btnCreateCourse.setOnClickListener {
            val intent = Intent(this, CreateCourseActivity::class.java)
            startActivity(intent)
        }
//        Jump to create activity
//        val btnCreateAct = findViewById<Button>(R.id.btnCreateAct)
//        btnCreateAct.setOnClickListener {
//            val intent = Intent(this, CreateActActivity::class.java)
//            startActivity(intent)
//        }
    }
}