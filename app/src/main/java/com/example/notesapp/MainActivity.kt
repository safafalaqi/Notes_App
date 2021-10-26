package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.database.DBhelper
import com.example.notesapp.database.Note
import com.example.notesapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvAdapter: RVAdapter

    var note = ""
    private var notes=ArrayList<Note>()
    private val databaseHelper by lazy{ DBhelper(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       //hide action bar
        supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btSubmit.setOnClickListener {
            note = binding.etNote.text.toString()

            if (note.isNotBlank()) {
                CoroutineScope(Dispatchers.IO).launch{
                    var status = databaseHelper.saveData(note)
                    notes = databaseHelper.retrieveData()
                    withContext(Dispatchers.Main) {
                        hideKeyboard()
                        binding.etNote.text.clear()
                        Toast.makeText(
                            this@MainActivity,
                            "data saved successfully! $status",
                            Toast.LENGTH_SHORT
                        ).show()
                        setRV()
                    }
                }

            } else
                Toast.makeText(
                    this,
                    "Field can not be empty! ",
                    Toast.LENGTH_SHORT
                ).show()
        }

        CoroutineScope(Dispatchers.IO).launch {
            notes = databaseHelper.retrieveData()
            //and if the array is not empty set recycler view
            if (notes.size >= 1) {
                withContext(Dispatchers.Main) {
                    setRV()
                }
            }
        }

        val swipeGesture = object:SwipeGesture(this){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                when(direction){
                    ItemTouchHelper.LEFT ->{rvAdapter.deleteNote(viewHolder.absoluteAdapterPosition)}
                    ItemTouchHelper.RIGHT ->{rvAdapter.updateItem(viewHolder.absoluteAdapterPosition)}
                }
            }
        }
          val touchHelper = ItemTouchHelper(swipeGesture)
          touchHelper.attachToRecyclerView(binding.rvList)
    }

    private fun setRV() {

        rvAdapter =RVAdapter(notes, this)
        binding.rvList.adapter = rvAdapter
        binding.rvList.layoutManager = LinearLayoutManager(applicationContext)

    }
    fun hideKeyboard()
    {
        // Hide Keyboard
        val hideKeyboard = ContextCompat.getSystemService(this, InputMethodManager::class.java)
        hideKeyboard?.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
    }


}