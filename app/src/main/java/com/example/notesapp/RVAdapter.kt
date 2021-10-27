package com.example.notesapp

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
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
import com.example.notesapp.database.NoteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RVAdapter(private var notes: List<Note>, val context: Context): RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root)

    private val noteDao by lazy{ NoteDatabase.getInstance(context).noteDao() }
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
        holder.itemView.setOnClickListener{

        }

    }

    fun deleteNote(i:Int){

        deleteAlert(i)
    }

    fun updateItem(i:Int){

      customAlert(i)

    }
    override fun getItemCount() = notes.size

    private fun customAlert(position:Int)
    {
        var note=""
        val dialog = Dialog(context)
        val dialogview = LayoutInflater.from(context)
            .inflate(R.layout.custom_dialog, null, false)
        //initializing dialog screen
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(true)
        dialog?.setContentView(dialogview)
        dialog?.show()

        val etUpdate = dialog.findViewById<EditText>(R.id.etUpdate)
        val btSubmit = dialog.findViewById<Button>(R.id.btUpdate)
        val btClose =dialog.findViewById<AppCompatImageButton>(R.id.imgBtClose)

        btSubmit.setOnClickListener{
            if(etUpdate.text.isNotBlank()){
                note= etUpdate.text.toString()
                if(note.isNotBlank()) {
                    CoroutineScope(Dispatchers.IO).launch{
                    notes[position].note=etUpdate.text.toString()
                    noteDao.updateNote(notes[position])
                        notes = noteDao.getNotes()
                        withContext(Dispatchers.Main){ notifyDataSetChanged()
                            dialog.dismiss()}
                }
            }else
                Toast.makeText(context,"Note can not be empty!",Toast.LENGTH_SHORT).show()
        }
        }
        btClose.setOnClickListener{
            dialog.cancel()
            notifyDataSetChanged()
        }

    }

    fun deleteAlert(i:Int){

        val dialogBuilder = android.app.AlertDialog.Builder(context)
        dialogBuilder.setMessage("Are you sure you want to delete the note?")
            // negative button text and action
            .setPositiveButton("yes",DialogInterface.OnClickListener {
                    dialog, id ->
                CoroutineScope(Dispatchers.IO).launch {
                    noteDao.deleteNote(notes[i])
                    notes = noteDao.getNotes()
                    withContext(Dispatchers.Main){ notifyDataSetChanged()}
                }

            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
                notifyDataSetChanged()
            })
        // create dialog box
        val alert = dialogBuilder.create()
        // show alert dialog
        alert.show()

    }

}