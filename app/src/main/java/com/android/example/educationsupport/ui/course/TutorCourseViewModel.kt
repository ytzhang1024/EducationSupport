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
class TutorCourseViewModel @Inject constructor(
    val repository: CourseRepository
): ViewModel() {

    private val _tuorCourse = MutableLiveData<UiState<List<Course>>>()
    val tutorCourse: LiveData<UiState<List<Course>>>
        get() = _tuorCourse


    fun getTutorCourseList() {
        _tuorCourse.value = UiState.Loading
        repository.getTutorCourseList() { _tuorCourse.value = it }
    }

}
