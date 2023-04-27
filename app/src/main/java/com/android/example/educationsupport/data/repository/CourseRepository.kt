package com.android.example.educationsupport.data.repository

import com.android.example.educationsupport.data.model.Course
import com.android.example.educationsupport.utils.UiState

interface CourseRepository {
    fun addCourse(course: Course, result: (UiState<Pair<Course,String>>) -> Unit)
    fun getCourseList(result: (UiState<List<Course>>) -> Unit)
    fun getTutorCourseList(result: (UiState<List<Course>>) -> Unit)
    fun getStudentCourseList(result: (UiState<List<Course>>) -> Unit)
}