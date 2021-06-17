package com.dhxxn.untilwhenaos.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.recyclerview.widget.RecyclerView
import com.dhxxn.untilwhenaos.App
import com.dhxxn.untilwhenaos.LoadingDialog
import com.dhxxn.untilwhenaos.PreferenceUtil
import com.dhxxn.untilwhenaos.adapter.DdayAdapter
import com.dhxxn.untilwhenaos.model.Dday
import com.dhxxn.untilwhenaos.network.RetrofitBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await

class MainViewModel(application: Application) : AndroidViewModel(application) {

    fun setInit(recyclerView: RecyclerView, context: Context) {
        val token = App.prefs.getString("token", "no token")
        val dialog = LoadingDialog(recyclerView.context)
        dialog.show()
        val list: ArrayList<Dday> = arrayListOf()
        RetrofitBuilder.api.getDdayAll(token).enqueue(object : Callback<List<Dday>> {
            override fun onResponse(call: Call<List<Dday>>, response: Response<List<Dday>>) {
                for (document in response.body()!!) {
                    val dday = Dday()
                    dday.id = document.id
                    dday.content = document.content
                    dday.finishDate = document.finishDate
                    dday.startDate = document.startDate
                    dday.totalRemainDates = document.totalRemainDates

                    list.add(dday)
                }
                val adapter = DdayAdapter(list)
                recyclerView.adapter = adapter
                dialog.dismiss()
            }

            override fun onFailure(call: Call<List<Dday>>, t: Throwable) {
                Toast.makeText(context, "서버에 연결이 되지 않았습니다. 다시 시도해주세요!", Toast.LENGTH_SHORT).show()
                Log.e("MainActivity!!!", t.message.toString())
            }
        })
    }

}