package com.dhxxn.untilwhenaos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.dhxxn.untilwhenaos.R
import com.dhxxn.untilwhenaos.model.Setting

class SettingAdapter (val context: Context, val setList: ArrayList<Setting>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_setting, null)

        val img = view.findViewById<ImageView>(R.id.item_setting_img)
        val id = view.findViewById<TextView>(R.id.item_setting_id)
        val set = view.findViewById<TextView>(R.id.item_setting_text)

        set.text = setList[position].set
        img.setImageDrawable(setList[position].img)
        id.text = setList[position].id

        return view
    }

    override fun getItem(position: Int): Any {
        return setList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return setList.size
    }

}