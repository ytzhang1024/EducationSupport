package com.android.example.educationsupport.data.model

import android.os.Parcelable


//data class Question (
//    var title: String? = null,
//    var option_A: String ?= null,
//    var option_B: String ?= null,
//    var option_C: String ?= null,
//    var option_D: String ?= null,
//    var correct_answer: String?= null
//): Parcelable
import android.os.Parcel

import kotlin.collections.MutableMap


data class QuizRecord(
    var activity_name: String? = null,
    var student_email: String? = null,
    var answer: MutableMap<String, String>? = null,
    var correct_answer: MutableMap<String, String>? = null,
    var mark: Int? = null,

)