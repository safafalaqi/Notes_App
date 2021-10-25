package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapp.database.DBhelper
import com.example.notesapp.database.Note
import com.example.notesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvAdapter: RVAdapter

    var note = ""
    private var notes=ArrayList<Note>()
    private val databaseHelper by lazy{ DBhelper(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btSubmit.setOnClickListener {
            note = binding.etNote.text.toString()

            if (note.isNotBlank()) {
                var status = databaseHelper.saveData(note)
                Toast.makeText(
                    applicationContext,
                    "data saved successfully! $status",
                    Toast.LENGTH_SHORT
                ).show()

                notes = databaseHelper.retrieveData()
                setRV()

            } else
                Toast.makeText(
                    applicationContext,
                    "Field can not be empty! ",
                    Toast.LENGTH_SHORT
                ).show()
        }


        notes = databaseHelper.retrieveData()
        //and if the array is not empty set recycler view
        if (notes.size >= 1) {
            setRV()
        }

    }

    private fun setRV() {
        rvAdapter =RVAdapter(notes, this)
        binding.rvList.adapter = rvAdapter
        binding.rvList.layoutManager = LinearLayoutManager(applicationContext)
    }

}