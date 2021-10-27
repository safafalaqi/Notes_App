package com.example.notesapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class],version = 1,exportSchema = false)
abstract class NoteDatabase: RoomDatabase() {

    companion object{
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getInstance(context: Context):NoteDatabase {
            if (INSTANCE != null) {
                return INSTANCE as NoteDatabase
            }
            synchronized(this){  //for the protection purpose from concurrent execution on multi threading
                val instance = Room.databaseBuilder(context.applicationContext, NoteDatabase::class.java, "notes"
                ).fallbackToDestructiveMigration()  // Destroys old database on version change
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

    abstract fun noteDao(): NoteDao

}


