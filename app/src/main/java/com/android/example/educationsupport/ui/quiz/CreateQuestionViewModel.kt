package com.android.example.educationsupport.ui.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.example.educationsupport.data.model.Question
import com.android.example.educationsupport.data.repository.CourseRepository
import com.android.example.educationsupport.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject



@HiltViewModel
class CreateQuestionViewModel @Inject constructor(
    val repository: CourseRepository
): ViewModel() {

    private val _addQuestion = MutableLiveData<UiState<Pair<Question,String>>>()
    val addQuestion: LiveData<UiState<Pair<Question, String>>>
        get() = _addQuestion

    fun addQuestion(activityName: String, question: Question) {
        _addQuestion.value = UiState.Loading
        repository.addQuestion(activityName, question) { _addQuestion.value = it }
    }
}