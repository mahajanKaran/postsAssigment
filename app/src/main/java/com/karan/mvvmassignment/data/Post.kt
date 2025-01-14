package com.karan.mvvmassignment.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    val id: String? = "",
    val title: String? = "",
    val body: String? = ""
) : Parcelable