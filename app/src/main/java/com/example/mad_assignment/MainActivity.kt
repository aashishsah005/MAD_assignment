package com.example.mad_assignment

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {

    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        emailField = findViewById(R.id.emailField)
        passwordField = findViewById(R.id.passwordField)

        // Setup signup button
        findViewById<Button>(R.id.signupButton).setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        // Setup login button
        findViewById<AppCompatButton>(R.id.loginButton).setOnClickListener {
            performLogin()
        }
    }

    private fun performLogin() {
        val email = emailField.text.toString().trim()
        val password = passwordField.text.toString()

        // Clear previous errors
        emailField.error = null
        passwordField.error = null

        // Validate email
        if (email.isEmpty()) {
            emailField.error = "Email is required"
            emailField.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailField.error = "Please enter a valid email"
            emailField.requestFocus()
            return
        }

        // Validate password
        if (password.isEmpty()) {
            passwordField.error = "Password is required"
            passwordField.requestFocus()
            return
        }

        if (password.length < 6) {
            passwordField.error = "Password must be at least 6 characters"
            passwordField.requestFocus()
            return
        }

        // Login successful - navigate to home
        Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish() // Close login screen so back button doesn't return here
    }
}