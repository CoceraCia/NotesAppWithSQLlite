package com.coceracia.sqlnotes.model

import kotlinx.coroutines.flow.Flow

class NoteRepo(private val noteDao: NoteDao) {

    val allNotes: Flow<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note) = noteDao.insert(note)
    suspend fun search(id: Long):Note? = noteDao.search(id)
    suspend fun update(note: Note) = noteDao.update(note)
    suspend fun delete(note: Note) = noteDao.delete(note)
}