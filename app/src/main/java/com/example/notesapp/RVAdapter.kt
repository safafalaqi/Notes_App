package com.example.notesapp

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.database.Note
import com.example.notesapp.databinding.ItemRowBinding
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton

class RVAdapter(val mainActivity: MainActivity): RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root)


    var notes= emptyList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val note = notes[position].note

        holder.binding.apply {
            tvTitle.text=note
        }

    }
    override fun getItemCount() = notes.size

    fun updateNote(i:Int) {

        val dialog = Dialog(mainActivity)
        val dialogView = LayoutInflater.from(mainActivity)
            .inflate(R.layout.custom_dialog, null, false)
        //initializing dialog screen
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(dialogView)
        dialog.show()

        val etUpdate = dialog.findViewById<EditText>(R.id.etUpdate)
        val btSubmit = dialog.findViewById<Button>(R.id.btUpdate)
        val btClose = dialog.findViewById<AppCompatImageButton>(R.id.imgBtClose)

        btSubmit.setOnClickListener {
            if (etUpdate.text.isNotBlank()) {
                val note = etUpdate.text.toString()
                if (note.isNotBlank()) {
                    notes[i].note = etUpdate.text.toString()
                    mainActivity.myViewModel.updateNote(notes[i])
                    dialog.dismiss()
                }
            } else
                Toast.makeText(mainActivity, "Note can not be empty!", Toast.LENGTH_SHORT).show()
        }

        btClose.setOnClickListener {
            dialog.cancel()
            notifyDataSetChanged()
        }
    }



    fun deleteNote(i:Int){

        val dialogBuilder = android.app.AlertDialog.Builder(mainActivity)
        dialogBuilder.setMessage("Are you sure you want to delete the note?")
            // negative button text and action
            .setPositiveButton("yes") { dialog, id ->
                mainActivity.myViewModel.deleteNote(notes[i])

            }
            .setNegativeButton("Cancel") { dialog, id ->
                dialog.cancel()
                notifyDataSetChanged()
            }
        // create dialog box
        val alert = dialogBuilder.create()
        // show alert dialog
        alert.show()

    }

    fun update(notesList: List<Note>){
        notes = notesList
        notifyDataSetChanged()
    }

}