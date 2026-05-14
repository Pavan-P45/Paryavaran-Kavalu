package com.example.paryavarankavalu

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btnLogout = findViewById<Button>(R.id.btnLogout)
        val btnReportWaste = findViewById<Button>(R.id.btnReportWaste)
        val btnViewReports = findViewById<Button>(R.id.btnViewReports)
        val btnMaps = findViewById<Button>(R.id.btnMaps)

        btnLogout.setOnClickListener {

            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        btnReportWaste.setOnClickListener {

            startActivity(Intent(this, ReportWasteActivity::class.java))
        }

        btnViewReports.setOnClickListener {

            startActivity(Intent(this, ViewReportsActivity::class.java))
        }

        btnMaps.setOnClickListener {

            startActivity(Intent(this, MapsActivity::class.java))
        }
    }
}