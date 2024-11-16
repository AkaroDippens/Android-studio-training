package com.example.firebaseproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebaseproject.databinding.ActivityUserMainBinding
import com.google.firebase.Firebase
import com.google.firebase.app
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class UserMainActivity : AppCompatActivity(), RecyclerViewInterface {

    private lateinit var binding: ActivityUserMainBinding
    private var user = Firebase.auth.currentUser?.email
    private var auth:FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var recyclerViewInterface:RecyclerViewInterface

    var db:FirebaseFirestore = FirebaseFirestore.getInstance()
    var noteList = ArrayList<Note>()
    private val noteReference = db.collection("notes")

    lateinit var recyclerAdapter:RecyclerNoteViewAdapter

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerViewInterface = this

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        binding.userText.text = "$user!"

        binding.addNoteButton.setOnClickListener{
            startActivity(Intent(this, NoteCreate::class.java))
        }

        binding.exitButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        getNotes { notes ->
            recyclerAdapter = RecyclerNoteViewAdapter(this, notes, recyclerViewInterface)
            recyclerView.adapter = recyclerAdapter
        }
    }

    private fun getNotes(callback: (List<Note>) -> Unit) {
        noteReference.whereEqualTo("userUid", auth.currentUser!!.uid).get().addOnSuccessListener { documents ->
            val noteList = documents.map { document ->
                Note(
                    noteUid = document["noteUid"] as String,
                    name = document["name"] as String,
                    desc = document["desc"] as String,
                    userUid = document["userUid"] as String
                )
            }
            callback(noteList)
        }
    }

    override fun onItemClick(note:Note?) {
        startActivity(Intent(this, NoteDetailActivity::class.java).putExtra("note",
        Gson().toJson(note).toString()))
    }

    override fun onDeleteClick(note:Note?) {
        if(note == null) return
        db.collection("notes").whereEqualTo("noteUid", note.noteUid).get()
            .addOnSuccessListener{
                for (document in it){
                    db.collection("notes").document(document.id)
                        .delete()
                        .addOnSuccessListener {
                            getNotes { notes -> recyclerAdapter.updateData(notes) }
                            Toast.makeText(this, "Note deleted successfully", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener{
                            Toast.makeText(this, "Failed to delete note: ${it.message}", Toast.LENGTH_SHORT).show()}
                }
            }        .addOnFailureListener{
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()}
    }
}