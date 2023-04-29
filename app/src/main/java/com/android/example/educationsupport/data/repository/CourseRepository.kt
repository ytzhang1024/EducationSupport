package com.android.example.educationsupport.data.repository

import com.android.example.educationsupport.data.model.Activity
import com.android.example.educationsupport.data.model.Course
import com.android.example.educationsupport.data.model.Question
import com.android.example.educationsupport.utils.UiState

interface CourseRepository {
    fun addCourse(course: Course, result: (UiState<Pair<Course,String>>) -> Unit)
    fun getCourseList(result: (UiState<List<Course>>) -> Unit)
    fun getTutorCourseList(result: (UiState<List<Course>>) -> Unit)
    fun getStudentCourseList(result: (UiState<List<Course>>) -> Unit)
    fun getActivityCourseList(courseName: String, result: (UiState<List<Activity>>) -> Unit)
    fun addActivity(activity: Activity, result: (UiState<Pair<Activity, String>>) -> Unit)
    fun addQuestion(activityName: String, question: Question, result: (UiState<Pair<Question, String>>) -> Unit)
    fun getQuestionList(activityName: String, result: (UiState<List<Question>>) -> Unit)
    fun getQuestionDetail(activityName: String, result: (UiState<Question>) -> Unit)
    fun enrollCourse(courseName: String, student_email: String)
    fun studentEnrollCourse(courseName: String?)
    fun checkStudentIfFinishTask(activityName: String, result: (Boolean) -> Unit)
}