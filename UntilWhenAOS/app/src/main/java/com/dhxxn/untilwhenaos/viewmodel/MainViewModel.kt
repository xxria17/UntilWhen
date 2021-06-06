package com.dhxxn.untilwhenaos.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.recyclerview.widget.RecyclerView
import com.dhxxn.untilwhenaos.DdayAdapter
import com.dhxxn.untilwhenaos.MySharedPreferences
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

    fun setInit(context: Context, recyclerView: RecyclerView, token: String) {
        //val token = MySharedPreferences.getUserToken(context)
        RetrofitBuilder.api.getDdayAll(token).enqueue(object : Callback<List<Dday>> {
            override fun onResponse(call: Call<List<Dday>>, response: Response<List<Dday>>) {
                val list: ArrayList<Dday> = arrayListOf()
                val data = response.body()
                if (data != null) {
                    for (document in data) {
                        val dday = Dday()
                        dday.content = document.content
                        dday.finishDate = document.finishDate
                        dday.startDate = document.startDate

                        list.add(dday)
                    }
                    val adapter = DdayAdapter(list)
                    recyclerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<List<Dday>>, t: Throwable) {
                Log.e("MainViewModel!!!", "message :: ${t.message.toString()}")
            }

        })
//        CoroutineScope(Dispatchers.Main).launch {
//            val list : ArrayList<Dday> = arrayListOf()
//            val data = getDataByCoroutine(token!!)
//            for (document in data) {
//                val dday = Dday()
//                dday.content = document.content
//                dday.finishDate = document.finishDate
//                dday.startDate = document.startDate
//
//                list.add(dday)
//            }
//            val adapter = DdayAdapter(list)
//            recyclerView.adapter = adapter
//        }
    }

    private suspend fun getDataByCoroutine(token: String): List<Dday> {
        return RetrofitBuilder.api.getDdayAll(token).await()
    }
}