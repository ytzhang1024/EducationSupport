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


data class Question(
    var title: String? = null,
    var option_A: String? = null,
    var option_B: String? = null,
    var option_C: String? = null,
    var option_D: String? = null,
    var correct_answer: String? = null
) : Parcelable {
    // Add default constructor
    constructor() : this("", "", "", "", "", "")

    // Parcelable implementation
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(title)
        dest.writeString(option_A)
        dest.writeString(option_B)
        dest.writeString(option_C)
        dest.writeString(option_D)
        dest.writeString(correct_answer)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Question> = object : Parcelable.Creator<Question> {
            override fun createFromParcel(source: Parcel): Question {
                return Question(
                    source.readString(),
                    source.readString(),
                    source.readString(),
                    source.readString(),
                    source.readString(),
                    source.readString()
                )
            }

            override fun newArray(size: Int): Array<Question?> {
                return arrayOfNulls(size)
            }
        }
    }
}
