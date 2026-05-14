package com.example.paryavarankavalu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore

class AdminReportAdapter(
    private val reportList: ArrayList<Report>
) : RecyclerView.Adapter<AdminReportAdapter.AdminViewHolder>() {

    class AdminViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val image =
            itemView.findViewById<ImageView>(R.id.adminReportImage)

        val description =
            itemView.findViewById<TextView>(R.id.adminReportDescription)

        val status =
            itemView.findViewById<TextView>(R.id.adminReportStatus)

        val btnCleaned =
            itemView.findViewById<Button>(R.id.btnMarkCleaned)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdminViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_admin_report, parent, false)

        return AdminViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reportList.size
    }

    override fun onBindViewHolder(holder: AdminViewHolder, position: Int) {

        val report = reportList[position]

        holder.description.text = report.description
        holder.status.text = "Status: ${report.status}"

        Glide.with(holder.itemView.context)
            .load(report.imageUrl)
            .into(holder.image)

        holder.btnCleaned.setOnClickListener {

            FirebaseFirestore.getInstance()
                .collection("reports")
                .get()
                .addOnSuccessListener { result ->

                    val documents = result.documents

                    if (position < documents.size) {

                        val docId = documents[position].id

                        FirebaseFirestore.getInstance()
                            .collection("reports")
                            .document(docId)
                            .update("status", "Cleaned")
                            .addOnSuccessListener {

                                holder.status.text =
                                    "Status: Cleaned"

                                Toast.makeText(
                                    holder.itemView.context,
                                    "Updated Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
                }
        }
    }
}