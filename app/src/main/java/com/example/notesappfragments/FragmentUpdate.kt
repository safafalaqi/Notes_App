package com.example.notesappfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.notesappfragments.data.Note
import org.w3c.dom.Text

class FragmentUpdate : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        /*when the fragment get the ViewModelProvider, it received the same
        SharedViewModel instance*/
       val  myViewModel = ViewModelProvider(requireActivity()).get(NoteViewModel::class.java)
        val args = arguments
        val note: Note? = args?.getSerializable("note") as Note?
        view.findViewById<TextView>(R.id.tvUpdateText).setText(note!!.note)
        val noteText=view.findViewById<EditText>(R.id.etNoteUpdate)
        view.findViewById<Button>(R.id.btUpdate).setOnClickListener{
            if(noteText.text.isNotBlank()) {
                note.note = noteText.text.toString()
                myViewModel.updateNote(note)
                findNavController().navigate(R.id.action_fragmentUpdate_to_fragmentHome)
            }
            else
                Toast.makeText(context,"Do not leave it empty!",Toast.LENGTH_SHORT).show()
        }
        view.findViewById<Button>(R.id.btBack).setOnClickListener{
                findNavController().navigate(R.id.action_fragmentUpdate_to_fragmentHome)
        }
        return view
    }

}