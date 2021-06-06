package com.dhxxn.untilwhenaos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dhxxn.untilwhenaos.model.Dday
import java.text.SimpleDateFormat

class DdayAdapter (private var list : MutableList<Dday>): RecyclerView.Adapter<DdayAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
        var date : TextView = itemView!!.findViewById(R.id.item_dday_date)
        var content : TextView = itemView!!.findViewById(R.id.item_dday_content)
        var remain : TextView = itemView!!.findViewById(R.id.item_dday_remainDate)

        fun bind(data : Dday) {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            date.text = simpleDateFormat.format(data.finishDate)
            content.text = data.content

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DdayAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dday, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: DdayAdapter.ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

}