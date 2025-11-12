package com.coceracia.sqlnotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.coceracia.sqlnotes.model.Note
import com.coceracia.sqlnotes.model.NoteDataBase
import com.coceracia.sqlnotes.model.NoteRepo
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application): AndroidViewModel(application) {
    private val repoNotes : NoteRepo
    val allNotes : LiveData<List<Note>>

    init {
        val noteDao = NoteDataBase.getDatabase(application).noteDao()
        repoNotes = NoteRepo(noteDao)
        allNotes = repoNotes.allNotes.asLiveData()
    }

    fun insert(note: Note) = viewModelScope.launch {
        repoNotes.insert(note)
    }

    suspend fun search(id: Long): Note? {
        return repoNotes.search(id)
    }

    fun update(note: Note) = viewModelScope.launch {
        repoNotes.update(note)
    }

    fun delete(note:Note) = viewModelScope.launch {
        repoNotes.delete(note)
    }

    suspend fun noteExists(id:Long): Boolean {
        return (search(id) != null)
    }

}