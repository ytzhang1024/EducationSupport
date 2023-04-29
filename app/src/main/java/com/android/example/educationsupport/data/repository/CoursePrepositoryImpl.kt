package com.android.example.educationsupport.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.android.example.educationsupport.data.model.Activity
import com.android.example.educationsupport.data.model.Course
import com.android.example.educationsupport.data.model.Question
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

        val courseRef = course.name?.let { database.collection("course").document(it)}
        val courseToActivityRef = course.name?.let {
            database.collection("courseActivityMapping").document(
                it
            )
        }
        val tutorToCourseRef = course.tutorEmail?.let {
            database.collection("tutorCourseMapping").document(it)
        }
        val courseEnable = mapOf(
            "enable" to true
        )
        database.runBatch { batch ->
            if (courseRef != null) {
                courseRef.set(course)
            }
            tutorToCourseRef?.update("course", FieldValue.arrayUnion(course.name))
            if (courseToActivityRef != null) {
                courseToActivityRef.get().addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        // Document exists
                    } else {
                        database.collection("courseActivityMapping").document(course.name!!).set(
                            courseEnable
                        )
                    }
                }.addOnFailureListener { exception ->
                    // Error handling
                }
            }
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
        auth.currentUser?.email?.let {
            database.collection("studentCourseMapping").document(it).get()
                .addOnSuccessListener { querySnapshot1 ->
                    val data = mutableListOf<String>()
                    val courses = querySnapshot1.get("course") as ArrayList<String>
                    data.addAll(courses)
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
    }

    override fun addActivity(activity: Activity, result: (UiState<Pair<Activity, String>>) -> Unit) {
        val activityRef = activity.title?.let { database.collection("activity").document(it) }
        val activityToCourseRef = activity.course?.let {
            database.collection("courseActivityMapping").document(
                it
            )
        }
        val activityToQuestionRef = activity.title?.let {
            database.collection("activityQuestionMapping").document(
                it
            )
        }
        val activityEnable = mapOf(
            "enable" to true
        )
        database.runBatch { batch ->
            if (activityRef != null) {
                activityRef.set(activity)
            }

            activityToCourseRef?.update("activity", FieldValue.arrayUnion(activity.title))

            if (activityToQuestionRef != null) {
                activityToQuestionRef.get().addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        // Document exists
                    } else {
                        database.collection("activityQuestionMapping").document(activity.title!!).set(
                            activityEnable
                        )
                    }
                }.addOnFailureListener { exception ->
                    // Error handling
                }
            }

        }.addOnCompleteListener {
            // ...
        }
    }



    override fun getActivityCourseList(courseName: String, result: (UiState<List<Activity>>) -> Unit) {
        val docRef = database.collection("courseActivityMapping").document(courseName)
        docRef.get().addOnSuccessListener { documentSnapshot ->
            val data = documentSnapshot.get("activity")
            if (data != null){
                data as List<String>
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

            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error getting document", exception)
            }
    }

    override fun addQuestion(
        activityName: String,
        question: Question,
        result: (UiState<Pair<Question, String>>) -> Unit
    ) {
        println(activityName)
        println(question)
        val questionRef = database.collection("question").document()
        val questionID = questionRef.id
        println(questionID)
        val activityToQuestionRef = database.collection("activityQuestionMapping").document(activityName)

        database.runBatch { batch ->
            questionRef.set(question)
            activityToQuestionRef.update("question", FieldValue.arrayUnion(questionID))
        }.addOnCompleteListener {
            // ...
        }
    }

    override fun getQuestionList(activityName: String, result: (UiState<List<Question>>) -> Unit) {
        val docRef = database.collection("activityQuestionMapping").document(activityName)
        docRef.get().addOnSuccessListener { documentSnapshot ->
            val data = documentSnapshot.get("question")
                if (data != null) {
                    data as List<String>
                    database.collection("question").get()
                        .addOnSuccessListener { querySnapshot2 ->
                            val questions = arrayListOf<Question>()
                            for (doc in querySnapshot2) {
                                val questionTmp = doc.toObject(Question::class.java)
                                questions.add(questionTmp)
                            }
                            result.invoke(
                                UiState.Success(questions)
                            )
                        }
                        .addOnFailureListener { e ->
                            Log.e("Firestore", "Error retrieving documents: $e")
                        }
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error getting document", exception)
            }
    }

    override fun getQuestionDetail(activityName: String, result: (UiState<Question>) -> Unit) {

    }

    override fun enrollCourse(courseName: String, student_email: String) {
        val docRef = database.collection("studentCourseMapping").document(student_email)
        docRef.get().addOnSuccessListener {
            
        }.addOnFailureListener {
            Log.e(TAG, "Fail to enroll")
        }
    }

    override fun studentEnrollCourse(courseName: String?) {
        val email = auth.currentUser?.email
        if (email != null) {
            database.collection("studentCourseMapping").document(email).update("course", FieldValue.arrayUnion(courseName))
        }
    }

    //这段代码写得很不优雅，我吐了
    override fun checkStudentIfFinishTask(activityName: String, result: (Boolean) -> Unit) {
        val email = auth.currentUser?.email
        if (email != null) {
            val ref = database.collection("quizResult").document(email)
            ref.get().addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val data = documentSnapshot.data
                    println(data)
                    if (data != null) {
                        if (data.get(activityName) != null) {
                            val tmp = data.get(activityName) as List<String>?
                            println(tmp)
                            if (tmp != null) {
                                if (tmp.size == 1) {
                                    result(false)
                                    return@addOnSuccessListener
                                }
                            }
                        }
                    }
                }
                result(true)
            }.addOnFailureListener {
                result(false)
            }
        } else {
            result(false)
        }
    }


}