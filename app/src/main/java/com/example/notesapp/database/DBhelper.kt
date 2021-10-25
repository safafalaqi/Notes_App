package com.example.notesapp.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBhelper (context: Context): SQLiteOpenHelper(context,"notes.db",null,1) {
    var sqLiteDatabase: SQLiteDatabase = writableDatabase
    override fun onCreate(db: SQLiteDatabase?) {
        if(db!=null){
            db.execSQL("create table notes (_id INTEGER PRIMARY KEY,Note text)")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
    }
    fun saveData(note:String): Long {
        val n= ContentValues()
        n.put("Note",note)
        var status =  sqLiteDatabase.insert("notes",null,n)
        return status
    }

    fun retrieveData(): ArrayList<Note> {
        val notes=ArrayList<Note>()
        var c=sqLiteDatabase.query("notes",null,null,null,null,null,null,null)

        //iterate through cursor and save to array list
        if (c != null) {
            while (c.moveToNext())
            {
                var note = Note(c.getString(0).toInt(),c.getString(1))

                notes.add(note)
            }
        }
        return notes
    }

}