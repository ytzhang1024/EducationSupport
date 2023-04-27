package com.android.example.educationsupport.ui.course

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.example.educationsupport.data.model.Course
import com.android.example.educationsupport.data.repository.CourseRepository
import com.android.example.educationsupport.utils.UiState
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AllCourseViewModel @Inject constructor(
    val repository: CourseRepository
): ViewModel() {

    private val _allCourse = MutableLiveData<UiState<List<Course>>>()
    val allCourse: LiveData<UiState<List<Course>>>
        get() = _allCourse


    fun getCourseList() {
        _allCourse.value = UiState.Loading
        repository.getCourseList() { _allCourse.value = it }
    }

}

