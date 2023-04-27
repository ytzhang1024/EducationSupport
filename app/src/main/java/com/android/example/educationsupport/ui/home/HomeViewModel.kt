package com.android.example.educationsupport.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    private  val _btn_text = MutableLiveData<String>().apply {
        value = "Hello"
    }
    val text: LiveData<String> = _text
//    val btn_text: LiveData<String> = _btn_text
}