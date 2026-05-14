package com.example.paryavarankavalu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ReportAdapter(
    private val reportList: ArrayList<Report>
) : RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {

    class ReportViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val image =
            itemView.findViewById<ImageView>(R.id.reportImage)

        val description =
            itemView.findViewById<TextView>(R.id.reportDescription)

        val status =
            itemView.findViewById<TextView>(R.id.reportStatus)

        val time =
            itemView.findViewById<TextView>(R.id.reportTime)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReportViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_report, parent, false)

        return ReportViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reportList.size
    }

    override fun onBindViewHolder(
        holder: ReportViewHolder,
        position: Int
    ) {

        val report = reportList[position]

        holder.description.text = report.description

        holder.status.text =
            "Status: ${report.status}"

        val sdf = SimpleDateFormat(
            "dd MMM yyyy - hh:mm a",
            Locale.getDefault()
        )

        val formattedTime =
            sdf.format(Date(report.timestamp))

        holder.time.text = formattedTime

        Glide.with(holder.itemView.context)
            .load(report.imageUrl)
            .into(holder.image)
    }
}