package com.android.example.educationsupport.ui.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.example.educationsupport.data.model.Course
import com.android.example.educationsupport.data.model.Question
import com.android.example.educationsupport.data.model.QuizRecord
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

    private val _studentQuestionDetail = MutableLiveData<UiState<List<Question>>>()
    val studentQuestionDetail: LiveData<UiState<List<Question>>>
        get() = _studentQuestionDetail

    fun studentGetQuestionDetail(activityName: String ) {

        repository.studentGetQuestionDetail(activityName) { _studentQuestionDetail.value = it }
    }

    private val _TutorQuestionDetail = MutableLiveData<UiState<List<Question>>>()
    val tutorQuestionDetail: LiveData<UiState<List<Question>>>
        get() = _TutorQuestionDetail

    fun tutorGetQuestionDetail(questionName: String ) {

        repository.tutorGetQuestionDetail(questionName) { _TutorQuestionDetail.value = it }
    }

    private val _deleteQuestion = MutableLiveData<UiState<Pair<Question, String>>>()
    val DeleteQuestion: LiveData<UiState<Pair<Question, String>>>
        get() =  _deleteQuestion
    fun deleteQuestion(activityName:String,questionName: String){
        repository.deleteQuestion(activityName,questionName) { _deleteQuestion.value = it }
    }

    private val _addQuizRecord = MutableLiveData<UiState<Pair<QuizRecord,String>>>()
    val addQuizRecord : LiveData<UiState<Pair<QuizRecord,String>>>
        get() = _addQuizRecord


    fun addQuizRecord(quizRecord: QuizRecord){
        _addQuizRecord.value = UiState.Loading
        repository.addQuizRecord(quizRecord) { _addQuizRecord.value = it }
    }


}