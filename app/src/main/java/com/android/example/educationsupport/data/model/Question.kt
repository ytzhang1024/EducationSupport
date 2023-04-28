package com.android.example.educationsupport.data.model

data class Question (
    var title: String? = null,
    var option_A: String ?= null,
    var option_B: String ?= null,
    var option_C: String ?= null,
    var option_D: String ?= null,
    var correct_answer: List<String>?= null
)