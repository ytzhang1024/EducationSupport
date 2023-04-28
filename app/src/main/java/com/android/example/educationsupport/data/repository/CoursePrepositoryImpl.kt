package com.android.example.educationsupport.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.android.example.educationsupport.data.model.Activity
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
                    data.addAll(array)
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
                    data.addAll(array)
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

    override fun addActivity(activity: Activity, result: (UiState<Pair<Activity, String>>) -> Unit) {
        val activityRef = activity.title?.let { database.collection("activity").document(it) }
        val activityToCourseRef = activity.course?.let {
            database.collection("courseActivityMapping").document(
                it
            )
        }

        database.runBatch { batch ->
            if (activityRef != null) {
                activityRef.set(activity)
            }

            activityToCourseRef?.update("activity", FieldValue.arrayUnion(activity.title))

        }.addOnCompleteListener {
            // ...
        }
    }

    override fun getActivityCourseList(courseName: String, result: (UiState<List<Activity>>) -> Unit) {
        val docRef = database.collection("courseActivityMapping").document(courseName)
        docRef.get().addOnSuccessListener { documentSnapshot ->
            val data = documentSnapshot.get("activity") as List<String>
            println("-----------------data--------------")
            println(data)

                database.collection("activity").get()
                    .addOnSuccessListener { querySnapshot2 ->
                        val activities = arrayListOf<Activity>()
                        for (doc in querySnapshot2) {
                            val activityTmp = doc.toObject(Activity::class.java)
                            if (data.contains(activityTmp.title)) {
                                activities.add(activityTmp)
                            }
                            Log.d("Firestore", doc.data.toString())
                        }
                        result.invoke(
                            UiState.Success(activities)
                        )
                    }
                    .addOnFailureListener { e ->
                        Log.e("Firestore", "Error retrieving documents: $e")
                    }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error getting document", exception)
            }
    }
}