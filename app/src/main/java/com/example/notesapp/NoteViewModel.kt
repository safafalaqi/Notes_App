package com.example.notesapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.notesapp.database.Note
import com.example.notesapp.database.NoteDatabase
import com.example.notesapp.database.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application){
    val notes:LiveData<List<Note>>
    val repository:NoteRepository

    init{
        val dao = NoteDatabase.getInstance(application).noteDao()
        repository = NoteRepository(dao)
        notes = repository.getNotes
    }


    fun deleteNote(note: Note) = viewModelScope.launch( Dispatchers.IO ){
        repository.delete(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch( Dispatchers.IO ){
        repository.update(note)
    }

    fun addNote(note: Note) = viewModelScope.launch( Dispatchers.IO ){
        repository.insert(note)
    }

    fun deleteAll() = viewModelScope.launch( Dispatchers.IO ){
        repository.deleteAll()
    }
}