package com.dhxxn.untilwhenaos.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.dhxxn.untilwhenaos.App
import com.dhxxn.untilwhenaos.model.Dday
import com.dhxxn.untilwhenaos.network.RetrofitBuilder
import com.dhxxn.untilwhenaos.view.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddViewModel(application: Application): AndroidViewModel(application) {

    fun createDday(finishDate : String, content: String, startDate: String, context: Context) {
        val dday = Dday(finishDate = finishDate, content = content, startDate = startDate)
        val token = App.prefs.getString("token", "no token")

        RetrofitBuilder.api.addDday(token, dday).enqueue(object : Callback<Dday> {
            override fun onResponse(call: Call<Dday>, response: Response<Dday>) {
                val code = response.code()
                if (code == 200) {
                    Toast.makeText(context, "저장되었습니다!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }
            }

            override fun onFailure(call: Call<Dday>, t: Throwable) {
                Toast.makeText(context, "서버에 연결이 되지 않았습니다. 다시 시도해주세요!", Toast.LENGTH_SHORT).show()
                Log.e("AddViewModel!!!", t.message.toString())
            }

        })
    }

    fun updateDDay(finishDate: String, content: String, startDate: String, context: Context, id :Int) {
        val dday = Dday(finishDate = finishDate, content = content, startDate = startDate)
        val token = App.prefs.getString("token", "no token")

        RetrofitBuilder.api.updateDday(token, id, dday).enqueue(object : Callback<Dday> {
            override fun onResponse(call: Call<Dday>, response: Response<Dday>) {
                val code = response.code()
                if (code == 200) {
                    Toast.makeText(context, "수정되었습니다!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }
            }

            override fun onFailure(call: Call<Dday>, t: Throwable) {
                Toast.makeText(context, "서버에 연결이 되지 않았습니다. 다시 시도해주세요!", Toast.LENGTH_SHORT).show()
                Log.e("AddViewModel!!!", t.message.toString())
            }

        })
    }
}