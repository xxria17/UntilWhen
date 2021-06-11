package com.dhxxn.untilwhenaos.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.recyclerview.widget.RecyclerView
import com.dhxxn.untilwhenaos.App
import com.dhxxn.untilwhenaos.PreferenceUtil
import com.dhxxn.untilwhenaos.adapter.DdayAdapter
import com.dhxxn.untilwhenaos.model.Dday
import com.dhxxn.untilwhenaos.network.RetrofitBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.await

class MainViewModel(application: Application) : AndroidViewModel(application) {

    fun setInit(recyclerView: RecyclerView) {
        val token = App.prefs.getString("token", "no token")
        CoroutineScope(Dispatchers.Main).launch {
            val list: ArrayList<Dday> = arrayListOf()
            val data = getDataByCoroutine(token)
            for (document in data) {
                val dday = Dday()
                dday.id = document.id
                dday.content = document.content
                dday.finishDate = document.finishDate
                dday.startDate = document.startDate
                dday.currentRemainDates = document.currentRemainDates
                dday.totalRemainDates = document.totalRemainDates

                list.add(dday)
            }
            val adapter = DdayAdapter(list)
            recyclerView.adapter = adapter
        }
    }

    private suspend fun getDataByCoroutine(token: String): List<Dday> {
        return RetrofitBuilder.api.getDdayAll(token).await()
    }
}