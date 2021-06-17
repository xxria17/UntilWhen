package com.dhxxn.untilwhenaos.view

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.dhxxn.untilwhenaos.App
import com.dhxxn.untilwhenaos.R
import com.dhxxn.untilwhenaos.databinding.ActivityMainBinding
import com.dhxxn.untilwhenaos.model.User
import com.dhxxn.untilwhenaos.network.RetrofitBuilder
import com.dhxxn.untilwhenaos.viewmodel.LoginViewModel
import com.dhxxn.untilwhenaos.viewmodel.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private var backKeyPressedTime : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.lifecycleOwner = this
        binding.main = viewModel

        binding.mainDdayList.layoutManager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this, VERTICAL)
        binding.mainDdayList.addItemDecoration(decoration)
        

        setUserId()
        viewModel.setInit(binding.mainDdayList, this)

        binding.mainSwipeLayout.setOnRefreshListener {
            viewModel.setInit(binding.mainDdayList, this)
            binding.mainSwipeLayout.isRefreshing = false
        }

        binding.mainAddBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, AddActivity::class.java)
            startActivity(intent)
        }

        binding.mainSettingBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, UserInfoActivity::class.java))
        }

    }

    private fun setUserId() {
        val userName = App.prefs.getString("id", "")
        val token = App.prefs.getString("token", "")
        RetrofitBuilder.api.getUserId(token, userName).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val userId = response.body()!!.id
                App.prefs.setString("userId", "$userId")
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("MainActivity!!!", t.message.toString())
            }

        })
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis()
            Toast.makeText(this, "뒤로가기 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
            return
        }

        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            moveTaskToBack(true)
            finish()
            android.os.Process.killProcess(android.os.Process.myPid())
        }
    }
}