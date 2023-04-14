package com.android.example.educationsupport.ui.quiz
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.android.example.educationsupport.R
import com.android.example.educationsupport.databinding.ActivityCreateCourseBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CreateCourseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateCourseBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val firestore = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_course)
        binding = ActivityCreateCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnSubmit = findViewById<Button>(R.id.btnSubmitCourse)
        btnSubmit.setOnClickListener {
            val tutor = "Reza"
            val name = binding.courseTitleET.text.toString()
            val description = binding.courseDesET.text.toString()

            val courseMap = hashMapOf (
                "tutor" to tutor,
                "name" to name,
                "description" to description
            )
            val courseId = FirebaseAuth.getInstance().currentUser!!.uid

            firestore.collection("course").document(courseId).set(courseMap).addOnSuccessListener {
                Toast.makeText(this, "Successful Create Course!", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Fail Create Course!", Toast.LENGTH_SHORT).show()
            }
            val intent = Intent(this, CreateModuleActivity::class.java)
            startActivity(intent)
        }

    }
}