package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.database.Note
import com.example.notesapp.database.NoteDatabase
import com.example.notesapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvAdapter: RVAdapter

    var note = ""
    //private lateinit var notes: List<Note>
    //declare note view model
     val myViewModel by lazy{ ViewModelProvider(this).get(NoteViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       //hide action bar
        supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        myViewModel.notes.observe(this, {list->
                list?.let { rvAdapter.update(it) }
        })

        setRV()

        binding.btSubmit.setOnClickListener {
            note = binding.etNote.text.toString()

            if (note.isNotBlank()) {
                    myViewModel.addNote(Note(0,note))
                        hideKeyboard()
                        binding.etNote.text.clear()
                        Toast.makeText(
                            this@MainActivity,
                            "data saved successfully!",
                            Toast.LENGTH_SHORT
                        ).show()
                       // setRV()

                } else
                Toast.makeText(
                    this,
                    "Field can not be empty! ",
                    Toast.LENGTH_SHORT
                ).show()
        }

        val swipeGesture = object:SwipeGesture(this){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                when(direction){
                    ItemTouchHelper.LEFT ->{rvAdapter.deleteNote(viewHolder.absoluteAdapterPosition)}
                    ItemTouchHelper.RIGHT ->{rvAdapter.updateNote(viewHolder.absoluteAdapterPosition)}
                }
            }
        }
          val touchHelper = ItemTouchHelper(swipeGesture)
          touchHelper.attachToRecyclerView(binding.rvList)
    }

    private fun setRV() {

        rvAdapter =RVAdapter( this)
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