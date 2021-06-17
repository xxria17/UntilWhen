package com.dhxxn.untilwhenaos.view

import android.app.DatePickerDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
    private val format : DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add)
        viewModel = ViewModelProvider(this).get(AddViewModel::class.java)
        binding.add = this
        binding.lifecycleOwner = this

        binding.addCloseBtn.setOnClickListener {
            finish()
        }

        val id = intent.getIntExtra("id", 0)

        // 수정일 때
        if (id != 0) {
            // 내용 넣어주기
            val content = intent.getStringExtra("content")
            binding.addEditContent.setText(content, TextView.BufferType.EDITABLE)

            binding.addSaveBtn.text = "수정"
        }

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
            val today = Date()
            var startDate = format.format(today)
            if (!isToday) {
                startDate = getSelectDate(binding.addStartPicker)
            }

            val builder = AlertDialog.Builder(this)
            val content = binding.addEditContent.text.toString().trim()
            builder.setTitle("${binding.addSaveBtn.text}")
            builder.setMessage("해당 내용을 ${binding.addSaveBtn.text}하시겠습니까?")
            builder.setPositiveButton("예") { dialog, which ->
                if (binding.addSaveBtn.text.equals("저장")) {
                    if (content.length == 0 || content.equals("")) {
                        Toast.makeText(applicationContext, "내용을 입력해주세요!", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    } else if (finishDate < startDate) {
                        Toast.makeText(applicationContext, "잘못된 날짜입니다! 다시 확인해주세요.", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    } else {
                        viewModel.createDday(finishDate, content, startDate, baseContext)
                    }
                } else {
                    if (content.length == 0 || content.equals("")) {
                        Toast.makeText(applicationContext, "내용을 입력해주세요!", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }  else if (finishDate < startDate) {
                        Toast.makeText(applicationContext, "잘못된 날짜입니다! 다시 확인해주세요.", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    } else {
                        viewModel.updateDDay(finishDate, binding.addEditContent.text.toString(), startDate, baseContext, id)
                    }
                    viewModel.updateDDay(finishDate, binding.addEditContent.text.toString(), startDate, baseContext, id)
                }
            }
            builder.setNegativeButton("아니오") { dialog, which ->
                return@setNegativeButton
            }
            builder.show()
        }
    }

    private fun getSelectDate(datePicker: DatePicker): String {
        var setFinishDate = ""
        var month = "${datePicker.month + 1}"
        var day = "${datePicker.dayOfMonth}"

        if (day.length < 2) {
            day = "0$day"
        }
        if (month.length < 2) {
            month = "0$month"
        }
        setFinishDate = "${datePicker.year}-${month}-${day}"

        return setFinishDate
    }

}