package com.example.firebaseproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firebaseproject.databinding.ActivityMainBinding
import com.example.firebaseproject.databinding.ActivityNoteCreateBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class NoteCreate : AppCompatActivity() {
    lateinit var binding:ActivityNoteCreateBinding
    private var auth:FirebaseAuth = FirebaseAuth.getInstance()
    var db:FirebaseFirestore = FirebaseFirestore.getInstance()

    var noteReference:CollectionReference = db.collection("notes")

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddNote.setOnClickListener(){
            var newNote = Note(
                UUID.randomUUID().toString(), binding.editTextTitle.text.toString(), binding.editTextDescription.text.toString(),
                auth.currentUser!!.uid)
            noteReference.add(newNote)
                .addOnSuccessListener {
                    Toast.makeText(this, "Заметка создана", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, UserMainActivity::class.java))
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
        }
    }
}