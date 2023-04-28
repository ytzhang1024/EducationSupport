package com.android.example.educationsupport.data.model


data class User(
    var id: String = "",
    var email: String = "",
    var firstName: String? = null,
    var lastName: String ?= null,
    var bio: String ?= null,
    var role: String ?= null
)
