package com.example.notesappfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesappfragments.adapter.RVAdapter
import com.example.notesappfragments.adapter.SwipeGesture
import com.example.notesappfragments.data.Note
import com.example.notesappfragments.databinding.FragmentHomeBinding


class FragmentHome : Fragment() {
    private lateinit var rvAdapter: RVAdapter
    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var note = ""
    //declare note view model
    lateinit var myViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_home, container, false)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
         view = binding.root

        /*when the fragment get the ViewModelProvider, it received the same
        SharedViewModel instance,*/
        myViewModel = ViewModelProvider(requireActivity()).get(NoteViewModel::class.java)


        myViewModel.notes.observe(viewLifecycleOwner, {list->
            list?.let { rvAdapter.update(list) }
        })

        setRV()

        binding.btSubmit.setOnClickListener {
            note =  binding.etNote.text.toString()

            if (binding.etNote.text.isNotBlank()) {
                myViewModel.addNote(Note("",note))
                hideKeyboard()
                binding.etNote.text.clear()
                Toast.makeText(
                    context,
                    "data saved successfully!",
                    Toast.LENGTH_SHORT
                ).show()
                // setRV()

            } else
                Toast.makeText(
                    context,
                    "Field can not be empty! ",
                    Toast.LENGTH_SHORT
                ).show()
        }

        val swipeGesture = object: SwipeGesture(requireContext()){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                when(direction){
                    ItemTouchHelper.LEFT ->{rvAdapter.deleteNote(viewHolder.absoluteAdapterPosition)}
                    ItemTouchHelper.RIGHT ->{rvAdapter.updateNote(viewHolder.absoluteAdapterPosition)}
                }
            }
        }
        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(binding.rvList)


        return view
    }

    private fun setRV() {

        rvAdapter = RVAdapter( this)
        binding.rvList.adapter = rvAdapter
        binding.rvList.layoutManager = LinearLayoutManager( requireContext())

    }
    fun hideKeyboard()
    {
        // Hide Keyboard
        val hideKeyboard = ContextCompat.getSystemService( requireContext(), InputMethodManager::class.java)
        hideKeyboard?.hideSoftInputFromWindow( getActivity()?.currentFocus?.windowToken, 0)
    }

}