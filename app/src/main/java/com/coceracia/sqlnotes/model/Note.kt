package com.coceracia.sqlnotes.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "note_table")
data class Note (
    var title: String,
    var date: String,
    var content: String,
    var lastModified: Long = System.currentTimeMillis(),
    @PrimaryKey val id: Long = System.currentTimeMillis()
): Parcelable