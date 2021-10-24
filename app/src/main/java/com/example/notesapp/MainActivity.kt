package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var etNote: EditText
    lateinit var btSubmit: Button
    var note = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etNote = findViewById(R.id.etNote)
        btSubmit = findViewById(R.id.btSubmit)
        btSubmit.setOnClickListener {
            note = etNote.text.toString()

            if(note.isNotEmpty()) {
                var dbhr = DBhelper(applicationContext)
                var status = dbhr.savedatd(note)
                Toast.makeText(
                    applicationContext,
                    "data saved successfully! " + status,
                    Toast.LENGTH_SHORT
                ).show()
            }else
                Toast.makeText(
                    applicationContext,
                    "Field can not be empty! ",
                    Toast.LENGTH_SHORT
                ).show()
        }
    }
}