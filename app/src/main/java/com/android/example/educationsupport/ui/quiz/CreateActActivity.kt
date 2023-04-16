package com.android.example.educationsupport.ui.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.example.educationsupport.R
import com.android.example.educationsupport.databinding.ActivityCreateActBinding
import com.android.example.educationsupport.repository.entity.Course
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CreateActActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateActBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private var firestore = Firebase.firestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var courseList: ArrayList<Course>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_act)
        binding = ActivityCreateActBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.recyclercourse)
        recyclerView.layoutManager = LinearLayoutManager(this)

        courseList = arrayListOf()
        firestore = FirebaseFirestore.getInstance()
        firestore.collection("course").get().addOnSuccessListener {
            if (!it.isEmpty) {
                for (data in it.documents) {
                    val course: Course? = data.toObject(Course::class.java)
                    if (course != null) {
                        courseList.add(course)
                    }
                }
                recyclerView.adapter = CourseAdapter(courseList)
            }
        }.addOnFailureListener {
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}