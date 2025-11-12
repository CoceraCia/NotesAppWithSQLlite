package com.coceracia.sqlnotes.viewmodel

import androidx.lifecycle.ViewModel
import com.coceracia.sqlnotes.model.Note

class MainActivityViewModel: ViewModel() {
    var listNotes = mutableListOf<Note>()


    fun noteExists(note: Note): Boolean {
        return !listNotes.all { it.id != note.id}
    }

    fun delete(note: Note): Boolean{
        listNotes.forEach {
            if (it.id == note.id){
                listNotes.remove(it)
                return true
            }
        }
        return false
    }

    fun updateValues(note:Note){
        listNotes.forEach {
            if (it.id == note.id){
                it.title = note.title
                it.date = note.date
                it.content = note.content
            }
        }
    }

}