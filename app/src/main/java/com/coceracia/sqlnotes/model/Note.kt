package com.coceracia.sqlnotes.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note (
    var title: String,
    var date: String,
    var content: String,
    val id: Long = System.currentTimeMillis()
): Parcelable