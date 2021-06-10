package com.dhxxn.untilwhenaos.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dhxxn.untilwhenaos.R
import com.dhxxn.untilwhenaos.model.Dday
import com.dhxxn.untilwhenaos.view.DetailActivity

class DdayAdapter (private var list : MutableList<Dday>): RecyclerView.Adapter<DdayAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
        var date : TextView = itemView!!.findViewById(R.id.item_dday_date)
        var content : TextView = itemView!!.findViewById(R.id.item_dday_content)
        var remain : TextView = itemView!!.findViewById(R.id.item_dday_remainDate)
        var progressBar : ProgressBar = itemView!!.findViewById(R.id.item_dday_progressBar)

        fun bind(data : Dday, context: Context) {
            //val current = (data.remain) -
            date.text = refactorDate(data.finishDate)
            content.text = data.content
            //progressBar.progress = (current / remain) * 100

            itemView.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("id", data.id)
                intent.putExtra("content", data.content)
                intent.putExtra("startDate", refactorDate(data.startDate))
                intent.putExtra("finishDate", refactorDate(data.finishDate))
                intent.putExtra("remain", data.remain)
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