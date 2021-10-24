package com.example.notesapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBhelper (context: Context): SQLiteOpenHelper(context,"notes.db",null,1) {
    var sqLiteDatabase: SQLiteDatabase = writableDatabase
    override fun onCreate(db: SQLiteDatabase?) {
        if(db!=null){
            db.execSQL("create table notes (_id integer primary key autoincrement,Note text)")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
    }
    fun savedatd(note:String): Long {
        val n= ContentValues()
        n.put("Note",note)
        var status =  sqLiteDatabase.insert("notes",null,n)
        return status
    }
}