package com.example.notesapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: Note)

    @Query("SELECT * FROM Notes")
    fun getNotes(): LiveData<List<Note>>

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("DELETE FROM Notes")
    suspend fun deleteAll() : Int


}