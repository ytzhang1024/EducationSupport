package com.android.example.educationsupport.ui.course

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.example.educationsupport.data.model.Course
import com.android.example.educationsupport.data.repository.CourseRepository
import com.android.example.educationsupport.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SelectedCourseViewModel @Inject constructor(
    val repository: CourseRepository
): ViewModel() {

    private val _studentCourse = MutableLiveData<UiState<List<Course>>>()
    val studentCourse: LiveData<UiState<List<Course>>>
        get() = _studentCourse


    fun getStudentCourseList() {
        _studentCourse.value = UiState.Loading
        repository.getStudentCourseList() { _studentCourse.value = it }
    }

}