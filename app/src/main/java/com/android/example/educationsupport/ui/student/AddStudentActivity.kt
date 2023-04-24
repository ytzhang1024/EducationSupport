package com.android.example.educationsupport.ui.student
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.android.example.educationsupport.R
import com.android.example.educationsupport.databinding.ActivityAddStudentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.ref.Reference

class AddStudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddStudentBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val firestore = Firebase.firestore
    private lateinit var auth: FirebaseAuth
    private lateinit var tutor_email: String
    private lateinit var course_name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_course)
        binding = ActivityAddStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //通过学生邮箱添加学生，学生账号会增加课程姓名（课程ID）
        val btnSubmit = findViewById<Button>(R.id.btnSubmitCourse)
        btnSubmit.setOnClickListener {
            val student_email = binding.studentEmailET.text.toString()
            auth = FirebaseAuth.getInstance()
            var bundle:Bundle?=intent.extras
            if(bundle!=null){
                course_name = bundle!!.getString("course_name").toString()
            }

            val courseMap = hashMapOf (
                " course_name" to  course_name
            )
            val courseId = FirebaseAuth.getInstance().currentUser!!.uid

            firestore.collection("student course").document(student_email).set(courseMap).addOnSuccessListener {
                Toast.makeText(this, "Successful Add Student!", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Fail Add Student!", Toast.LENGTH_SHORT).show()
            }
        }

    }
}