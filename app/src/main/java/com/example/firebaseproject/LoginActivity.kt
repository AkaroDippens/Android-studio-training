package com.example.firebaseproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.firebaseproject.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding
    private lateinit var auth:FirebaseAuth
    var db:FirebaseFirestore = FirebaseFirestore.getInstance()
    var userReference:CollectionReference = db.collection("users")

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        val emailInput = binding.emailInput
        val passwordInput = binding.passwordInput

        binding.regButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.logButton.setOnClickListener {
            val email:String = emailInput.text.toString().trim()
            val password:String = passwordInput.text.toString().trim()

            loginUser(email, password)
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val currentUser = auth.currentUser
                    if (currentUser != null) {
                        userReference.whereEqualTo("email", email).get()
                            .addOnSuccessListener { querySnapshot ->
                                if (!querySnapshot.isEmpty) {
                                    Toast.makeText(this, "Authentication successful!", Toast.LENGTH_SHORT)
                                        .show()
                                    val intent = Intent(this, UserMainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(this, "User not found in Firestore", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Firestore query failed: ${e.message}", Toast.LENGTH_SHORT)
                                    .show()
                            }
                    } else {
                        Toast.makeText(this, "Authentication failed (currentUser is null)", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(this, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }
}