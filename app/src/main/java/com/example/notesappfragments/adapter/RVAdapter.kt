package com.example.notesappfragments.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.notesappfragments.data.Note
import com.example.notesappfragments.databinding.ItemRowBinding
import com.example.notesappfragments.FragmentHome
import com.example.notesappfragments.R
import android.os.Bundle





class RVAdapter(val fragmentHome: FragmentHome): RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root)


    var notes = emptyList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val note = notes[position].note

        holder.binding.apply {
            tvTitle.text = note
        }

    }

    override fun getItemCount() = notes.size

    fun updateNote(i: Int) {
        val bundle = Bundle()
        bundle.putSerializable("note", notes[i]) // Serializable Object
        fragmentHome.findNavController().navigate(R.id.action_fragmentHome_to_fragmentUpdate,bundle)
        //notifyDataSetChanged()
    }


    fun deleteNote(i: Int) {
        val dialogBuilder = android.app.AlertDialog.Builder(fragmentHome.context)
        dialogBuilder.setMessage("Are you sure you want to delete the note?")
            // negative button text and action
            .setPositiveButton("yes") { dialog, id ->
                fragmentHome.myViewModel.deleteNote(notes[i])
            }
            .setNegativeButton("Cancel") { dialog, id ->
                dialog.cancel()
            }
        notifyDataSetChanged()
        // create dialog box
        val alert = dialogBuilder.create()
        // show alert dialog
        alert.show()

    }

    fun update(notesList: List<Note>) {
        notes = notesList
        notifyDataSetChanged()
    }
}