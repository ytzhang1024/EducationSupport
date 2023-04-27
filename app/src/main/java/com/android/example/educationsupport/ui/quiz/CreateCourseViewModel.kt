package com.android.example.educationsupport.ui.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.example.educationsupport.data.model.Course
import com.android.example.educationsupport.data.repository.CourseRepository
import com.android.example.educationsupport.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CreateCourseViewModel @Inject constructor(
    val repository: CourseRepository
): ViewModel() {

    private val _addCourse = MutableLiveData<UiState<Pair<Course,String>>>()
    val addCourse: LiveData<UiState<Pair<Course,String>>>
        get() = _addCourse

    fun addCourse(Course: Course){
        _addCourse.value = UiState.Loading
        repository.addCourse(Course) { _addCourse.value = it }
    }
}

