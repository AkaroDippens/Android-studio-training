package com.example.firebaseproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firebaseproject.databinding.ActivityNoteDetailBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class NoteDetailActivity : AppCompatActivity() {
    lateinit var binding:ActivityNoteDetailBinding
    var db:FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val note: Note = Gson().fromJson(intent.getStringExtra("note"), Note::class.java)
        binding.editTextTitle.setText(note.name)
        binding.editTextDescription.setText(note.desc)

        binding.btnEditNote.setOnClickListener{
            db.collection("notes").whereEqualTo("noteUid", note.noteUid).get().addOnSuccessListener{
                for (document in it){
                    db.collection("notes").document(document.id).set(mapOf(
                        "desc" to binding.editTextDescription.text.toString(),
                        "name" to binding.editTextTitle.text.toString(),
                        "noteUid" to note.noteUid,
                        "userUid" to note.userUid
                    ))
                        .addOnSuccessListener {
                            Toast.makeText(this, "Data has updated", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, UserMainActivity::class.java))
                            finish()
                        }
                        .addOnFailureListener { Toast.makeText(this, it.message, Toast.LENGTH_LONG).show() }
                }
            }
                .addOnFailureListener { Toast.makeText(this, it.message, Toast.LENGTH_LONG).show() }
        }
    }
}