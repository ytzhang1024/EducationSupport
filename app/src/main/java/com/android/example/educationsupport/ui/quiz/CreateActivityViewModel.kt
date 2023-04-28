package com.android.example.educationsupport.ui.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.example.educationsupport.data.model.Activity
import com.android.example.educationsupport.data.repository.CourseRepository
import com.android.example.educationsupport.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateActivityViewModel @Inject constructor(
    val repository: CourseRepository
): ViewModel() {

    private val _addActivity = MutableLiveData<UiState<Pair<Activity,String>>>()
    val addActivity: LiveData<UiState<Pair<Activity, String>>>
        get() = _addActivity

    fun addActivity(activity: Activity){
        _addActivity.value = UiState.Loading
        repository.addActivity(activity) { _addActivity.value = it }
    }
}