package com.android.example.educationsupport.ui.course

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.example.educationsupport.data.model.Activity
import com.android.example.educationsupport.data.model.Course
import com.android.example.educationsupport.data.repository.CourseRepository
import com.android.example.educationsupport.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CourseDetailViewModel @Inject constructor(
    val repository: CourseRepository
): ViewModel() {

    private val _courseActivity = MutableLiveData<UiState<List<Activity>>>()
    val courseActivity: LiveData<UiState<List<Activity>>>
        get() = _courseActivity


    fun getCourseActivityList(courseName: String) {
        _courseActivity.value = UiState.Loading
        repository.getActivityCourseList(courseName) { _courseActivity.value = it }
    }

}