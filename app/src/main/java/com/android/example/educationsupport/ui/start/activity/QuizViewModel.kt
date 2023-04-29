package com.android.example.educationsupport.ui.start.activity


import androidx.lifecycle.*
import com.android.example.educationsupport.data.repository.CourseRepository
import com.android.example.educationsupport.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    val repository: CourseRepository,
): ViewModel() {

    private val _isFinish = MutableLiveData<Boolean>()
    val isFinish: LiveData<Boolean>
        get() = _isFinish

    fun checkStudentIfFinishTest(activityName: String) {
        repository.checkStudentIfFinishTask(activityName) { _isFinish.value = it }
    }


}