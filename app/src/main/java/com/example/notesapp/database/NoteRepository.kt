package com.example.notesapp.database

import androidx.lifecycle.LiveData

class NoteRepository(private val dao:NoteDao) {

    val getNotes:LiveData<List<Note>> = dao.getNotes()

    suspend fun insert(note: Note){
        dao.addNote(note)
    }

    suspend fun update(note: Note){
        dao.updateNote(note)
    }

    suspend fun delete(note: Note) {
        dao.deleteNote(note)
    }

    suspend fun deleteAll(): Int {
        return dao.deleteAll()
    }

}