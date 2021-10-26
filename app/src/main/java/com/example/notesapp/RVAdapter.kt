package com.example.notesapp

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.database.DBhelper
import com.example.notesapp.database.Note
import com.example.notesapp.databinding.ItemRowBinding
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton


class RVAdapter(private var notes: ArrayList<Note>, val context: Context): RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root)

    private val databaseHelper by lazy{ DBhelper(context) }
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
        databaseHelper.deleteNote(notes[i])
        notes=databaseHelper.retrieveData()
        notifyDataSetChanged()
    }

    fun updateItem(i:Int){

      customAlert(i)

    }
    override fun getItemCount() = notes.size

    private fun customAlert(position:Int):String
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
                    databaseHelper.updateData(notes[position].id, note)
                    notes = databaseHelper.retrieveData()
                }
                notifyDataSetChanged()
                dialog.dismiss()
            }else
                Toast.makeText(context,"Note can not be empty!",Toast.LENGTH_SHORT).show()
        }
        btClose.setOnClickListener{
            dialog.cancel()
            notifyDataSetChanged()
        }
        return note
    }

}