package com.dhxxn.untilwhenaos.view

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.dhxxn.untilwhenaos.R
import com.dhxxn.untilwhenaos.databinding.ActivityDetailBinding
import com.dhxxn.untilwhenaos.network.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.detail = this

        binding.detailBackBtn.setOnClickListener {
            finish()
        }

        val id = intent.getIntExtra("id", 0)
        val startDate = intent.getStringExtra("startDate")
        val finishDate = intent.getStringExtra("finishDate")
        val content = intent.getStringExtra("content")
        val remain = intent.getStringExtra("remain")

        binding.detailStartDate.text = startDate
        binding.detailFinishDate.text = finishDate
        binding.detailContent.text = content
        binding.detailDdayDate.text = remain

        binding.detailMenuBtn.setOnClickListener {
            val list = arrayOf("수정", "삭제")

            val builder : AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("메뉴")
            builder.setItems(list) { _, which ->
                val selected = list[which]
                if (selected.equals("수정")) {
                    Toast.makeText(this, "수정을 선택하였습니다.", Toast.LENGTH_SHORT).show()
                } else if (selected.equals("삭제")){
                    RetrofitBuilder.api.deleteDday(id).enqueue(object : Callback<String> {
                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            Log.d("DetailActivity!!!", response.message())
                            Toast.makeText(this@DetailActivity, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@DetailActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }

                        override fun onFailure(call: Call<String>, t: Throwable) {
                            Log.e("DetailActivity!!!", t.message.toString())
                        }

                    })
                }
            }

            builder.show()
        }

    }
}