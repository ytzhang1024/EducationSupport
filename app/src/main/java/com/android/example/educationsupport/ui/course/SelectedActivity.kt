package com.android.example.educationsupport.ui.course

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.android.example.educationsupport.R
import com.android.example.educationsupport.ui.quiz.UnitActivity
import com.android.example.educationsupport.databinding.ActivitySelectedBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import android.widget.Toast
import com.android.example.educationsupport.ui.quiz.QuizActivity

class SelectedActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectedBinding
    private var firebaseAuth: FirebaseAuth? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var tutor_email: String
    private lateinit var course_name: String
    private val firestore = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected)

        binding = ActivitySelectedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        // multiple buttons to jump to the same page
//        callData().forEach(data -> {
//            val button = new Button(data.name)
//            button.onclick{
//                unitActivityPage()
//            }
//        })
        //Showing selected
        val courseId = FirebaseAuth.getInstance().currentUser!!.uid
        println(courseId)
        val ref = firestore.collection("course").document(courseId)
        ref.get().addOnSuccessListener {
            if (it != null) {
                  course_name = it.data?.get("name").toString()
//                val full_name = it.data?.get("firstName").toString() + " " + it.data?.get("lastName").toString()
//                val bio = it.data?.get("bio").toString()
//                val role  = it.data?.get("role").toString()
//
                  binding.btnCoursetest.setText(course_name)
//                binding.tvEmail.setText(u_email)
//                binding.userBio.setText(bio)
//                binding.userRole.setText(role)
            }
        }
                    .addOnFailureListener {
                        Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show() }

//        binding.btnCourse.setOnClickListener {
//            unitActivityPage()
//        }
        binding.btnCoursetest.setOnClickListener {
            val intent = Intent(this, UnitActivity::class.java)
            //Passing on values
            intent.putExtra("course_name",course_name)
            startActivity(intent)
        }
    }


    fun unitActivityPage() {
        val intent = Intent(this, UnitActivity::class.java)
        startActivity(intent)
    }

}