package com.example.paryavarankavalu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class ViewReportsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var reportList: ArrayList<Report>
    private lateinit var adapter: ReportAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_reports)

        recyclerView = findViewById(R.id.recyclerReports)

        recyclerView.layoutManager = LinearLayoutManager(this)

        reportList = ArrayList()

        adapter = ReportAdapter(reportList)

        recyclerView.adapter = adapter

        FirebaseFirestore.getInstance()
            .collection("reports")
            .get()
            .addOnSuccessListener { result ->

                for (document in result) {

                    val report = document.toObject(Report::class.java)

                    reportList.add(report)
                }

                adapter.notifyDataSetChanged()
            }
    }
}