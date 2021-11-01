package com.example.notesappfirebase

import android.util.Log
import androidx.lifecycle.*
import com.example.notesappfirebase.data.Note
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel() : ViewModel(){
    var notes: MutableLiveData<List<Note>> = MutableLiveData()
    val db = Firebase.firestore
    val TAG = "In_NoteViewModel"


    init{
        notes = getFireBaseNotes()
    }

     fun getFireBaseNotes(): MutableLiveData<List<Note>> {
        val allNotes = arrayListOf<Note>()
        db.collection("notes").get().addOnSuccessListener { result ->
            result.forEach{
                it.data.map { (key, value) -> allNotes.add(Note(it.id,value.toString()))}
                /*val note = it.toObject(Note::class.java)
                if(note !=null)
                {
                    allNotes.add(note)
                }*/
            }
            notes.value =allNotes
        }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error occurs while getting data.", exception)
            }

         return notes
    }


    fun deleteNote(note: Note) = viewModelScope.launch( Dispatchers.IO ){
        db.collection("notes").document(note.id).delete()
        //to update notes
        getFireBaseNotes()
    }

    fun updateNote(note: Note) = viewModelScope.launch( Dispatchers.IO ){
        db.collection("notes").document(note.id).update("note",note.note)
        //to update notes
        getFireBaseNotes()
    }

    fun addNote(n: Note)  = viewModelScope.launch( Dispatchers.IO ){
        val note = hashMapOf("note" to n.note)
        db.collection("notes").add(note) .addOnSuccessListener { documentReference ->
            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
        }.addOnFailureListener { e -> Log.w(TAG, "Error adding document", e) }
        //to update notes
       getFireBaseNotes()
    }

    fun deleteAll()  = CoroutineScope(Dispatchers.IO).launch( Dispatchers.IO ){

    }
}