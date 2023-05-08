package com.android.example.educationsupport.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.android.example.educationsupport.data.model.*
import com.android.example.educationsupport.databinding.ActivityQuizDetailBinding
import com.android.example.educationsupport.utils.UiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlin.math.log

class CourseRepositoryImpl(
    val auth: FirebaseAuth,
    val database: FirebaseFirestore,
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
                println("===========================")
                println("courses:"+courses)
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
        val student_email = auth.currentUser?.email
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
            if (activityRef != null) {
                activityRef.update("student", FieldValue.arrayUnion(student_email))
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
        //改question id 为title
        val questionRef = question.title?.let { database.collection("question").document(it) }
            //val questionRef = database.collection("question").document()

        //val questionID = questionRef.id
        val activityToQuestionRef = database.collection("activityQuestionMapping").document(activityName)
        //val activityToQuestionRef = database.collection("activityQuestionMapping").document()


        database.runBatch { batch ->
            if (questionRef != null) {
                questionRef.set(question)
            }
            activityToQuestionRef.update("question", FieldValue.arrayUnion(question.title))
        }.addOnCompleteListener {
            // ...
        }
    }

    override fun deleteQuestion(
        activityName:String,
        questionName: String,
        result: (UiState<Pair<Question, String>>) -> Unit
    ) {
        val questionRef = database.collection("question").document(questionName)
        questionRef.delete()
            .addOnSuccessListener {

                println("----------------- delete successfully")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error retrieving documents: $e")
            }
        val activityToQuestionRef = database.collection("activityQuestionMapping").document(activityName)
        activityToQuestionRef.update("question", FieldValue.arrayRemove(questionName))
            .addOnSuccessListener {
                println("----------------- question removed from activityQuestionMapping")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error removing question from activityQuestionMapping: $e")
            }
    }

    override fun getQuestionList(activityName: String, result: (UiState<List<Question>>) -> Unit) {
        val docRef = database.collection("activityQuestionMapping").document(activityName)

        docRef.get().addOnSuccessListener { documentSnapshot ->
            //get question id
            val data = documentSnapshot.get("question")
            println("get question list:"+data)
                if (data != null) {
                    data as List<String>
                    database.collection("question").get()
                        .addOnSuccessListener { querySnapshot2 ->
                            val questions = arrayListOf<Question>()

                            for (doc in querySnapshot2) {
                                val questionTmp = doc.toObject(Question::class.java)

                                if (data.contains(questionTmp.title)) {
                                   questions.add(questionTmp)
                                }


                                //questions.add(questionTmp)
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

    override fun studentGetQuestionDetail(questionName: String, result: (UiState<List<Question>>) -> Unit) {
        if (questionName != null) {
//                data as List<String>
            database.collection("question").get()
                .addOnSuccessListener { querySnapshot2 ->
                    val questions = arrayListOf<Question>()

                    for (doc in querySnapshot2) {
                        val questionTmp = doc.toObject(Question::class.java)

                        if (questionName == questionTmp.title) {
                            questions.add(questionTmp)
                            println("????????????????????")
                            println("tutorquestions:"+questions)
                            println("tutorquestionTmp:"+questionTmp)

                        }


                        //questions.add(questionTmp)
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

    override fun addQuizRecord(quizRecord: QuizRecord, result: (UiState<Pair<QuizRecord, String>>) -> Unit){
        quizRecord.student_email = auth.currentUser?.email



        val id = quizRecord.student_email + quizRecord.activity_name
        val quizRef = id.let { database.collection("quizRecord").document(it) }
        //val questionRef = database.collection("question").document()

        //val questionID = questionRef.id
//        val studentToQuizRef = quizRecord.student_email?.let {
//                database.collection("studentActivityMapping").document(
//                    it
//                )
//            }
        val studentToQuizRef =
            quizRecord.student_email?.let {
                database.collection("studentActivityMapping").document(
                    it
                )
            }
        println("----------------------------test studentQuizMapping:"+studentToQuizRef)
        //val activityToQuestionRef = database.collection("activityQuestionMapping").document()


        database.runBatch { batch ->
            if (quizRef != null) {
                quizRef.set(quizRecord)
            }
//            if (quizRef != null) {
//                quizRef.update("answer", FieldValue.arrayUnion(quizRecord.answer))
//            }
            if (studentToQuizRef != null) {
                studentToQuizRef.get().addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        studentToQuizRef.update("activity", FieldValue.arrayUnion(quizRecord.activity_name))
                    } else {
                        database.collection("studentActivityMapping").document(quizRecord.student_email!!)
                            .set(mapOf("activity" to listOf(quizRecord.activity_name)))
                    }
                }.addOnFailureListener { exception ->
                    // Error handling
                }
            }

        }.addOnCompleteListener {
            // ...
        }
    }


override fun studentGetReview(studentEmail: String, activityName: String, result: (UiState<List<QuizRecord>>) -> Unit) {
    var email: String? = null
    if(studentEmail == "studentEmail"){
        email = auth.currentUser?.email
    }else{
        email = studentEmail
    }

    val id = email + activityName

    database.collection("quizRecord")
        .get()
        .addOnSuccessListener { documents ->
            val quizRecord = arrayListOf<QuizRecord>()
            for (document in documents) {
                println("--------------------Get review document:"+document)
                val answerMap = document.get("answer") as? Map<String, String> ?: emptyMap()
                val correctAnswerMap = document.get("correct_answer") as? Map<String, String> ?: emptyMap()
                val quizRecordItem = QuizRecord(
                    document.getString("activity_name"),
                    document.getString("student_email"),
                    answerMap.toMutableMap(),
                    correctAnswerMap.toMutableMap(),
                    document.getLong("mark")?.toInt()
                )
                if(email == document.getString("student_email")){
                    if(activityName == document.getString("activity_name")){
                        quizRecord.add(quizRecordItem)
                        println("---------------test student answer:"+quizRecordItem.answer)
                    }
                }

            }
            result.invoke(UiState.Success(quizRecord))
        }
        .addOnFailureListener {
            result.invoke(UiState.Failure(it.localizedMessage))
        }
}



    override fun tutorGetQuestionDetail(questionName: String, result: (UiState<List<Question>>) -> Unit) {
        //val binding: ActivityQuizDetailBinding
//        val docRef = database.collection("question").document(questionName)
//        docRef.get().addOnSuccessListener { documentSnapshot ->
//            //get question id
//            val data = documentSnapshot.get("title")
//            println("!!!!!!!!!!!!!in the tutorGetQuestionDetail"+docRef)
            if (questionName != null) {
//                data as List<String>
                database.collection("question").get()
                    .addOnSuccessListener { querySnapshot2 ->
                        val questions = arrayListOf<Question>()

                        for (doc in querySnapshot2) {
                            val questionTmp = doc.toObject(Question::class.java)

                            if (questionName == questionTmp.title) {
                                questions.add(questionTmp)
                                println("????????????????????")
                                println("tutorquestions:"+questions)
                                println("tutorquestionTmp:"+questionTmp)

                            }


                            //questions.add(questionTmp)
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



   //
   //The teacher adds the student to the course by entering the student's email address
   override fun enrollCourse(courseName: String, student_email: String) {
        val docRef = database.collection("studentCourseMapping").document(student_email)
        docRef.get().addOnSuccessListener {
            
        }.addOnFailureListener {
            Log.e(TAG, "Fail to enroll")
        }
    }

    override fun studentEnrollActivity(activityName: String?) {
        val email = auth.currentUser?.email
            if (activityName != null) {
                database.collection("activity").document(activityName).update("students", FieldValue.arrayUnion(email))
                println("----------------------add student email to activity document")
            }
//        if (activityName != null) {
//            database.collection("activity").document(activityName).update("students", FieldValue.arrayUnion(email))
//            println("----------------------add student email to activity document")
//        }
    }


override fun getStudentList(activityName: String, result: (UiState<List<User>>) -> Unit) {
    val docRef = database.collection("activity").document(activityName)
    docRef.get().addOnSuccessListener { documentSnapshot ->
        val data = documentSnapshot.get("students") //get activity student field
        println("--------------------test data:"+data)

        if (data != null){
            data as List<String>
            database.collection("user").get()
                .addOnSuccessListener { querySnapshot2 ->
                    val users = arrayListOf<User>()

                    for (doc in querySnapshot2) {
                        val userTmp = doc.toObject(User::class.java)

                        if (data.contains(userTmp.email)) {

                            users.add(userTmp)
                        }
                        Log.d("Firestore", doc.data.toString())
                    }
                    result.invoke(
                        UiState.Success(users)
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

    override fun getCompletedActivitList(result: (UiState<List<Activity>>) -> Unit) {
        var studentEmail = auth.currentUser?.email
        val docRef = studentEmail?.let { database.collection("studentActivityMapping").document(it) }
        if (docRef != null) {
            docRef.get().addOnSuccessListener { documentSnapshot ->
                val data = documentSnapshot.get("activity") //get activity student field
                println("--------------------test data:"+data)

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

    override fun addStudent(studentEmail: String,courseName: String){
        println("111111111-1---------------------")
        println(studentEmail)
        println(courseName)
        val Ref = database.collection("studentCourseMapping").document(studentEmail)
        Ref.update("course", FieldValue.arrayUnion(courseName)).addOnSuccessListener {
            println("Success")
        }.addOnFailureListener {
            println("Fail")
        }
    }


}