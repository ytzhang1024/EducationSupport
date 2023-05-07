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


data class Review(
    var title: String? = null,
    var option_A: String? = null,
    var option_B: String? = null,
    var option_C: String? = null,
    var option_D: String? = null,
    var student_answer: String? = null,
    var correct_answer: String? = null

)