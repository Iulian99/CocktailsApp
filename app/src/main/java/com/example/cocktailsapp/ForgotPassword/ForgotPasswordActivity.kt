package com.example.cocktailsapp.ForgotPassword

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cocktailsapp.R
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var emailEditText: EditText
    private lateinit var resetPasswordButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        auth = FirebaseAuth.getInstance()

        emailEditText = findViewById(R.id.emailEditText)
        resetPasswordButton = findViewById(R.id.resetPasswordButton)

        resetPasswordButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()

            if (email.isEmpty()) {
                emailEditText.error = "Email is required"
                emailEditText.requestFocus()
                return@setOnClickListener
            }

            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Password reset email sent", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}