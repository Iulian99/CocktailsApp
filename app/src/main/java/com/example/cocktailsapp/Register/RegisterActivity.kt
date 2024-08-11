package com.example.cocktailsapp.Register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cocktailsapp.Authentification.AuthentificationActivity
import com.example.cocktailsapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.util.TextUtils
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {
    private lateinit var editTextFirstName: EditText
    private lateinit var editTextLastName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonRegister: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var textRegister: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        editTextFirstName = findViewById(R.id.editTextFirstName)
        editTextLastName = findViewById(R.id.editTextLastName)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonRegister = findViewById(R.id.buttonRegister)
        textRegister = findViewById(R.id.textSignIn)

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        buttonRegister.setOnClickListener {
            registerUser()
        }
        textRegister.setOnClickListener{
            val intent = Intent(this, AuthentificationActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun registerUser() {
        val firstName = editTextFirstName.text.toString().trim()
        val lastName = editTextLastName.text.toString().trim()
        val email = editTextEmail.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

        if (TextUtils.isEmpty(firstName)) {
            editTextFirstName.error = "First name is required"
            return
        }
        if (TextUtils.isEmpty(lastName)) {
            editTextLastName.error = "Last name is required"
            return
        }
        if (TextUtils.isEmpty(email)) {
            editTextEmail.error = "Email is required"
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.error = "Enter a valid email"
            return
        }
        if (TextUtils.isEmpty(password)) {
            editTextPassword.error = "Password is required"
            return
        }
        if (password.length < 6) {
            editTextPassword.error = "Password must be at least 6 characters"
            return
        }

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user: FirebaseUser? = mAuth.currentUser
                    user?.let {
                        val userId = it.uid
                        val userMap = hashMapOf(
                            "firstName" to firstName,
                            "lastName" to lastName,
                            "email" to email
                        )
                        db.collection("users").document(userId)
                            .set(userMap)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Registration successful.", Toast.LENGTH_SHORT)
                                    .show()
                                Log.d("RegisterActivity", "User profile is created for $userId")
                            }
                            .addOnFailureListener { e ->
                                Log.w("RegisterActivity", "Error adding document", e)
                                Toast.makeText(
                                    this,
                                    "Registration failed: ${e.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
                }
                else {
                    Log.w("RegisterActivity", "createUserWithEmail:failure", task.exception) // Afisare mesaj cand utilizatorul s-a inregistrat cu succes
                    if (task.exception is FirebaseAuthUserCollisionException) {
                        Toast.makeText(
                            this,
                            "User with this email already exists.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else{
                        Toast.makeText(
                            this,
                            "Authentication failed: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
    }
}