package com.android.example.educationsupport.ui.quiz
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import com.android.example.educationsupport.R
import com.android.example.educationsupport.data.model.Course
import com.android.example.educationsupport.databinding.ActivityCreateCourseBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateCourseActivity : AppCompatActivity() {

    val TAG: String = "CreateCourseActivity"
    private lateinit var binding: ActivityCreateCourseBinding
    private val createCourseViewModel: `CreateCourseViewModel` by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_course)
        binding = ActivityCreateCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnSubmit = findViewById<Button>(R.id.btnSubmitCourse)
        btnSubmit.setOnClickListener {

            var course = Course(
                name = binding.courseTitleET.text.toString(),
                description = binding.courseDesET.text.toString()
            )
            createCourseViewModel.addCourse(course)

            Toast.makeText(this, "Create Successful", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

}