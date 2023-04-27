package com.android.example.educationsupport.data.repository

import android.util.Log
import com.android.example.educationsupport.data.model.Course
import com.android.example.educationsupport.utils.UiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class CourseRepositoryImpl(
    val auth: FirebaseAuth,
    val database: FirebaseFirestore
):CourseRepository {

    override fun addCourse(course: Course, result: (UiState<Pair<Course, String>>) -> Unit) {
        course.tutorEmail = auth.currentUser?.email
        course.tutorName = auth.currentUser?.displayName
        val courseRef = course.name?.let { database.collection("course").document(it) }
        val courseToTutorRef = course.tutorEmail?.let {
            database.collection("tutorCourseMapping").document(
                it
            )
        }

        database.runBatch { batch ->
            if (courseRef != null) {
                courseRef.set(course)
            }

            courseToTutorRef?.update("course", FieldValue.arrayUnion(course.name))

        }.addOnCompleteListener {
            // ...
        }
    }

    override fun getCourseList(result: (UiState<List<Course>>) -> Unit){
        database.collection("course")
            .get()
            .addOnSuccessListener {
                val courses = arrayListOf<Course>()
                for (document in it) {
                    val courseTmp = document.toObject(Course::class.java)
                    courses.add(courseTmp)
                }
                result.invoke(
                    UiState.Success(courses)
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    override fun getTutorCourseList(result: (UiState<List<Course>>) -> Unit) {
        val tutorCourseRef =
            auth.currentUser?.email?.let { database.collection("tutorCourseMapping").document(it) }

        // First query to retrieve data from a collection
        database.collection("tutorCourseMapping").get()
            .addOnSuccessListener { querySnapshot1 ->
                val data = mutableListOf<String>()
                for (doc in querySnapshot1) {
                    val array = doc.get("course") as ArrayList<String>
                    println("--------------doc----------")
                    println(doc)
                    data.addAll(array)
                    println("------------data--------")
                    println(data)
                }

                // Use the retrieved data to perform another query
                database.collection("course").get()
                    .addOnSuccessListener { querySnapshot2 ->
                        val courses = arrayListOf<Course>()
                        // Process the results of the second query
                        for (doc in querySnapshot2) {
                            val courseTmp = doc.toObject(Course::class.java)
                            if (data.contains(courseTmp.name)) {
                                courses.add(courseTmp)
                            }

//                            Log.d("Firestore", doc.data.toString())
                        }
                        result.invoke(
                            UiState.Success(courses)
                        )
                    }
                    .addOnFailureListener { e ->
                        Log.e("Firestore", "Error retrieving documents: $e")
                    }
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error retrieving documents: $e")
            }
    }

    override fun getStudentCourseList(result: (UiState<List<Course>>) -> Unit) {
        // First query to retrieve data from a collection
        database.collection("studentCourseMapping").get()
            .addOnSuccessListener { querySnapshot1 ->
                val data = mutableListOf<String>()
                for (doc in querySnapshot1) {
                    val array = doc.get("course") as ArrayList<String>
                    println("--------------doc----------")
                    println(doc)
                    data.addAll(array)
                    println("------------data--------")
                    println(data)
                }

                // Use the retrieved data to perform another query
                database.collection("course").get()
                    .addOnSuccessListener { querySnapshot2 ->
                        val courses = arrayListOf<Course>()
                        // Process the results of the second query
                        for (doc in querySnapshot2) {
                            val courseTmp = doc.toObject(Course::class.java)
                            if (data.contains(courseTmp.name)) {
                                courses.add(courseTmp)
                            }
                            Log.d("Firestore", doc.data.toString())
                        }
                        result.invoke(
                            UiState.Success(courses)
                        )
                    }
                    .addOnFailureListener { e ->
                        Log.e("Firestore", "Error retrieving documents: $e")
                    }
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error retrieving documents: $e")
            }
    }
}