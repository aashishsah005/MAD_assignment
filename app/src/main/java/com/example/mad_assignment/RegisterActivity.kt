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

class RegisterActivity : AppCompatActivity() {

    private lateinit var nameField: EditText
    private lateinit var phoneField: EditText
    private lateinit var emailField: EditText
    private lateinit var addressField: EditText
    private lateinit var passwordField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initializeViews()

        findViewById<AppCompatButton>(R.id.btn_register).setOnClickListener {
            performRegistration()
        }

        findViewById<Button>(R.id.btn_login_register).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun initializeViews() {
        nameField = findViewById(R.id.nameText)
        phoneField = findViewById(R.id.numberText)
        emailField = findViewById(R.id.mailText)
        addressField = findViewById(R.id.addressText)
        passwordField = findViewById(R.id.passwordText)
    }

    private fun performRegistration() {
        val name = nameField.text.toString().trim()
        val phone = phoneField.text.toString().trim()
        val email = emailField.text.toString().trim()
        val address = addressField.text.toString().trim()
        val password = passwordField.text.toString()

        // Clear previous errors
        nameField.error = null
        phoneField.error = null
        emailField.error = null
        addressField.error = null
        passwordField.error = null

        // Validate name
        if (name.isEmpty()) {
            nameField.error = "Name is required"
            nameField.requestFocus()
            return
        }

        if (name.length < 3) {
            nameField.error = "Name must be at least 3 characters"
            nameField.requestFocus()
            return
        }

        // Validate phone
        if (phone.isEmpty()) {
            phoneField.error = "Phone number is required"
            phoneField.requestFocus()
            return
        }

        if (phone.length != 10) {
            phoneField.error = "Phone number must be 10 digits"
            phoneField.requestFocus()
            return
        }

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

        // Validate address
        if (address.isEmpty()) {
            addressField.error = "Address is required"
            addressField.requestFocus()
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

        // Registration successful
        Toast.makeText(this, "Registration successful! Please login.", Toast.LENGTH_LONG).show()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}