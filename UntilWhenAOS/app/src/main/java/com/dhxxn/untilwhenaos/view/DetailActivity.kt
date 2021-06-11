package com.dhxxn.untilwhenaos.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.dhxxn.untilwhenaos.R
import com.dhxxn.untilwhenaos.databinding.ActivityDetailBinding
import com.dhxxn.untilwhenaos.viewmodel.DetailViewModel
import kotlin.math.abs

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.detail = this
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        binding.detailBackBtn.setOnClickListener {
            finish()
        }

        val id = intent.getIntExtra("id", 0)
        val startDate = intent.getStringExtra("startDate")
        val finishDate = intent.getStringExtra("finishDate")
        val content = intent.getStringExtra("content")
        val remain = intent.getLongExtra("remain", 0)

        binding.detailStartDate.text = startDate
        binding.detailFinishDate.text = finishDate
        binding.detailContent.text = content

        if (remain > 0) {
            binding.detailDdaySt.text = "오늘은 기준일부터"
            binding.detailDdayDate.text = "$remain"
            binding.detailDdaySt2.text = "째 되는 날입니다."
            binding.detailTvHighlight.setBackgroundColor(ContextCompat.getColor(this, R.color.pink))
            var drawable : Drawable = binding.view5.background
            drawable = DrawableCompat.wrap(drawable)
            DrawableCompat.setTint(drawable, ContextCompat.getColor(this, R.color.pink))
            binding.view5.background = drawable
        } else {
            binding.detailDdayDate.text = "${abs(remain)}"
            binding.detailDdayDate.setTextColor(ContextCompat.getColor(this, R.color.sky_blue))
        }

        binding.detailMenuBtn.setOnClickListener {
            val list = arrayOf("수정", "삭제")

            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("메뉴")
            builder.setItems(list) { _, which ->
                val selected = list[which]
                if (selected.equals("수정")) {
                    Toast.makeText(this, "수정을 선택하였습니다.", Toast.LENGTH_SHORT).show()
                    viewModel.update(this, id, startDate, finishDate, content)
                } else if (selected.equals("삭제")) {
                    viewModel.delete(this, id)
                }
            }

            builder.show()
        }

    }
}