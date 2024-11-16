package com.example.firebaseproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.firebaseproject.databinding.ActivityMainBinding
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var firebaseAuth = FirebaseAuth.getInstance()
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    var userReference: CollectionReference = db.collection("users")

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (firebaseAuth.currentUser?.email != null) {
            startActivity(Intent(this, UserMainActivity::class.java))
        }

        val emailInput = binding.emailInput
        val passwordInput = binding.passwordInput

        binding.logButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.regButton.setOnClickListener {
            val email: String = emailInput.text.toString().trim()
            val password: String = passwordInput.text.toString().trim()

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                emailInput.error = "Неверный формат почты"
            else if (password.length < 6)
                passwordInput.error = "Пароль слишком короткий"
            else registerUser(email, password)
        }
    }

    private fun registerUser(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult: AuthResult ->
                val fireBaseUser: FirebaseUser? = authResult.user

                val newUser = User(fireBaseUser!!.uid, fireBaseUser.email)
                userReference.add(newUser)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Пользователь " + fireBaseUser.email + " зарегистрирован",
                            Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
    }
}