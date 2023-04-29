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
class CourseViewModel @Inject constructor(
    val repository: CourseRepository
): ViewModel() {
    fun studentEnrollCourse(courseName: String?) {
        repository.studentEnrollCourse(courseName)
    }

    private val _courseDetail = MutableLiveData<UiState<Course>>()
    val courseDetail: LiveData<UiState<Course>>
        get() = _courseDetail


//    fun getCourseDetail(courseDetail: String) {
//        _courseDetail.value = UiState.Loading
//        repository.getCourseDetail(courseDetail) { _courseDetail.value = it }
//    }

}
