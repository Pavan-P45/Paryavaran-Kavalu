package com.example.paryavarankavalu

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class AdminDashboardActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var reportList: ArrayList<Report>
    private lateinit var adapter: AdminReportAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        recyclerView = findViewById(R.id.adminRecyclerView)

        val btnLogout =
            findViewById<Button>(R.id.btnAdminLogout)

        recyclerView.layoutManager =
            LinearLayoutManager(this)

        reportList = ArrayList()

        adapter = AdminReportAdapter(reportList)

        recyclerView.adapter = adapter

        FirebaseFirestore.getInstance()
            .collection("reports")
            .get()
            .addOnSuccessListener { result ->

                reportList.clear()

                for (document in result) {

                    val report =
                        document.toObject(Report::class.java)

                    reportList.add(report)
                }

                adapter.notifyDataSetChanged()
            }

        btnLogout.setOnClickListener {

            startActivity(
                Intent(this, LoginActivity::class.java)
            )

            finish()
        }
    }
}