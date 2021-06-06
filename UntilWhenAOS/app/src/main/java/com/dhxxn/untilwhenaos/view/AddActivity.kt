package com.dhxxn.untilwhenaos.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.dhxxn.untilwhenaos.R
import com.dhxxn.untilwhenaos.databinding.ActivityAddBinding
import com.dhxxn.untilwhenaos.viewmodel.AddViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    private lateinit var viewModel : AddViewModel
    var isToday : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add)
        viewModel = ViewModelProvider(this).get(AddViewModel::class.java)
        binding.add = this
        binding.lifecycleOwner = this

        binding.addCloseBtn.setOnClickListener {
            finish()
        }

        val token = intent.getStringExtra("token") ?: ""

        binding.addRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.add_today_radio) {
                binding.addStartPicker.visibility = View.GONE
            } else {
                binding.addStartPicker.visibility = View.VISIBLE
                isToday = false
            }
        }

        binding.addSaveBtn.setOnClickListener {

            // 끝나는 날
            val finishDate = getSelectDate(binding.addDdayPicker)

            // 시작하는 날
            var startDate = "2021-06-07"
            if (!isToday) {
                startDate = getSelectDate(binding.addStartPicker)
            }


            viewModel.createDday(finishDate, binding.addEditContent.text.toString(), startDate, token, this)
        }
    }

    private fun getSelectDate(datePicker: DatePicker): String {
        var selectDate : Date
        var setFinishDate : String = ""
        val format : DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN)
        var month : String = "${datePicker.month + 1}"
        var day : String = "${datePicker.dayOfMonth}"
        if (day.length < 2) {
            day = "0$day"
        }
        if (month.length < 2) {
            month = "0$month"
        }
        setFinishDate = "${datePicker.year}-${month}-${day}"
        selectDate = format.parse(setFinishDate)

        return setFinishDate
    }

}