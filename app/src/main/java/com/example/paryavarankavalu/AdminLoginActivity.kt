package com.example.paryavarankavalu

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AdminLoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)

        val email = findViewById<EditText>(R.id.etAdminEmail)
        val password = findViewById<EditText>(R.id.etAdminPassword)
        val loginBtn = findViewById<Button>(R.id.btnAdminLogin)

        loginBtn.setOnClickListener {

            val adminEmail = email.text.toString()
            val adminPassword = password.text.toString()

            if (
                adminEmail == "1da22cs108.cs@drait.edu.in"
                &&
                adminPassword == "1DA22CS108"
            ) {

                Toast.makeText(
                    this,
                    "Admin Login Successful",
                    Toast.LENGTH_SHORT
                ).show()

                startActivity(
                    Intent(this, AdminDashboardActivity::class.java)
                )

            } else {

                Toast.makeText(
                    this,
                    "Invalid Admin Credentials",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}