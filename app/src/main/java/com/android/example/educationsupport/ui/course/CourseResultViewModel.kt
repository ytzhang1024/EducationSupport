package com.android.example.educationsupport.ui.course

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.example.educationsupport.data.model.Activity
import com.android.example.educationsupport.data.repository.CourseRepository
import com.android.example.educationsupport.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CourseResultViewModel @Inject constructor(
    val repository: CourseRepository
): ViewModel() {

    private val _completedActivityList = MutableLiveData<UiState<List<Activity>>>()
    val completedActivityList: LiveData<UiState<List<Activity>>>
        get() = _completedActivityList


    fun getCompletedActivitList() {
        _completedActivityList.value = UiState.Loading
        repository.getCompletedActivitList() { _completedActivityList.value = it }
    }

}
