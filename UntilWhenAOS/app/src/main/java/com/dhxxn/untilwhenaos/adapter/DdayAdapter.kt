package com.dhxxn.untilwhenaos.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dhxxn.untilwhenaos.R
import com.dhxxn.untilwhenaos.model.Dday
import com.dhxxn.untilwhenaos.view.DetailActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

class DdayAdapter (private var list : MutableList<Dday>): RecyclerView.Adapter<DdayAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
        var date : TextView = itemView!!.findViewById(R.id.item_dday_date)
        var content : TextView = itemView!!.findViewById(R.id.item_dday_content)
        var remain : TextView = itemView!!.findViewById(R.id.item_dday_remainDate)
        var progressBar : ProgressBar = itemView!!.findViewById(R.id.item_dday_progressBar)

        val format = SimpleDateFormat("yyyy-MM-dd")
        val today = Date()

        fun bind(data : Dday, context: Context) {
            val remainData = (today.time - format.parse(data.finishDate).time) / (24*60*60*1000)

            val total = abs(data.totalRemainDates)
            val current = total - abs(remainData)

            if (remainData > 0) {
                remain.text = "+${remainData}"
                val a = Math.pow(10.0, (remain.text.toString().substring(1).length+1).toDouble()).toInt()
                progressBar.progress = (100 * a / 10).toInt()
            } else {
                remain.text = "${remainData}"
                progressBar.progress = (100 * current / total).toInt()
            }
            date.text = refactorDate(data.finishDate)
            content.text = data.content


            itemView.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("id", data.id)
                intent.putExtra("content", data.content)
                intent.putExtra("startDate", refactorDate(data.startDate))
                intent.putExtra("finishDate", refactorDate(data.finishDate))
                intent.putExtra("remain", remainData)
                context.startActivity(intent)
            }
        }
    }

    fun refactorDate(date : String?): String {
        val slice = date!!.substring(0,10)
        val split = slice.split("-")

        return "${split[0]}년 ${split[1]}월 ${split[2]}일"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dday, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], holder.content.context)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}