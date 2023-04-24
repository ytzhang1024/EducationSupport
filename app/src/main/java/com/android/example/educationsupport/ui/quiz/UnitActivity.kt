package com.android.example.educationsupport.ui.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.example.educationsupport.R
import com.android.example.educationsupport.databinding.ActivityUnitBinding
import com.android.example.educationsupport.ui.student.AddStudentActivity
import android.widget.Toast

class UnitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUnitBinding
    private lateinit var course_name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unit)
        binding = ActivityUnitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Get values
        var bundle:Bundle?=intent.extras
        if(bundle!=null){
            course_name = bundle!!.getString("course_name").toString()
        }
        binding.btnActivity.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            startActivity(intent)
        }
        binding.btnAddStudent.setOnClickListener {
            val intent = Intent(this, AddStudentActivity::class.java)
            intent.putExtra("course_name",course_name)
            startActivity(intent)
            startActivity(intent)
        }
    }
}