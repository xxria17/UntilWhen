package com.dhxxn.untilwhenaos.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dhxxn.untilwhenaos.App
import com.dhxxn.untilwhenaos.network.RetrofitBuilder
import com.dhxxn.untilwhenaos.view.AddActivity
import com.dhxxn.untilwhenaos.view.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application): AndroidViewModel(application) {

    fun delete(context: Context, id : Int) {
        val token = App.prefs.getString("token", "no token")

        RetrofitBuilder.api.deleteDday(token, id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Toast.makeText(context, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                context.startActivity(intent)
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("DetailActivity!!!", t.message.toString())
            }

        })
    }

    fun update(context: Context, id: Int, startDate: String?, finishDate: String?, content: String?) {
        val intent = Intent(context, AddActivity::class.java)
        intent.putExtra("id", id)
        intent.putExtra("startDate", startDate)
        intent.putExtra("finishDate", finishDate)
        intent.putExtra("content", content)
        context.startActivity(intent)
    }

}