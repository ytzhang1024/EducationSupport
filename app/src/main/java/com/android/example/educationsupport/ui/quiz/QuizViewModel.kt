package com.android.example.educationsupport.ui.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.example.educationsupport.data.model.Course
import com.android.example.educationsupport.data.model.Question
import com.android.example.educationsupport.data.repository.CourseRepository
import com.android.example.educationsupport.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class QuizViewModel @Inject constructor(
    val repository: CourseRepository
): ViewModel() {

    private val _allQuestion = MutableLiveData<UiState<List<Question>>>()
    val allQuestion: LiveData<UiState<List<Question>>>
        get() = _allQuestion


    fun getQuestionList(activityName: String) {
        _allQuestion.value = UiState.Loading
        repository.getQuestionList(activityName) { _allQuestion.value = it }
    }

    private val _questionDetail = MutableLiveData<UiState<Question>>()
    val questionDetail: LiveData<UiState<Question>>
        get() = _questionDetail

    fun getQuestionDetail(activityName: String) {
        _questionDetail.value = UiState.Loading
        repository.getQuestionDetail(activityName) { _questionDetail.value = it }
    }
}