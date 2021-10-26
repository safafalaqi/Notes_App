package com.example.notesapp.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.lang.Exception

class DBhelper (val context: Context): SQLiteOpenHelper(context,"notes.db",null,1) {
    var sqLiteDatabase: SQLiteDatabase = writableDatabase
    override fun onCreate(db: SQLiteDatabase?) {
        if(db!=null){
            db.execSQL("CREATE TABLE notes (_id INTEGER PRIMARY KEY autoincrement,Note text)")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS notes")
        onCreate(sqLiteDatabase)
    }
    fun saveData(note:String): Long {
        val n= ContentValues()
        n.put("Note",note)
        var status =  sqLiteDatabase.insert("notes",null,n)
        return status
    }

    fun retrieveData(): ArrayList<Note> {
        val notes=ArrayList<Note>()
        var c=sqLiteDatabase.rawQuery("SELECT * FROM notes",null)

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

    fun updateData(pk: Int, note: String){

        try {
            val cv=ContentValues()
            cv.put("Note",note)
            //UPDATE notes SET Note=?
            var status= sqLiteDatabase.update("notes",cv,"_id = $pk",null)
            Toast.makeText(context, "Update success! $status", Toast.LENGTH_SHORT)
                .show()

        }catch (e: Exception){
            Toast.makeText(context,"Can not Update! ",  Toast.LENGTH_SHORT  ).show()

        }
    }

    fun deleteNote(note:Note) {
        try {
            sqLiteDatabase.delete("notes","_id = ${note.id}",null)
            Toast.makeText(context,"Delete success! ",  Toast.LENGTH_SHORT  ).show()
        }catch (e: Exception){
            Toast.makeText(context,"Can not delete! ",  Toast.LENGTH_SHORT  ).show()
        }
    }

}