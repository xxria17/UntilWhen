package com.dhxxn.untilwhenaos.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.dhxxn.untilwhenaos.App
import com.dhxxn.untilwhenaos.R
import com.dhxxn.untilwhenaos.adapter.SettingAdapter
import com.dhxxn.untilwhenaos.model.Setting
import com.dhxxn.untilwhenaos.network.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserInfoActivity : AppCompatActivity() {

    private lateinit var list : ListView
    private lateinit var closeBtn : ImageView
    private lateinit var deleteBtn : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        init()
        val userId = App.prefs.getString("id","")

        val settingList = arrayListOf<Setting>(
            Setting(getDrawable(R.drawable.smiling),"로그인 되어있는 아이디", userId),
            Setting(getDrawable(R.drawable.logout), "로그아웃", "")
        )

        val settingAdapter = SettingAdapter(this, settingList)
        list.adapter = settingAdapter
        list.setOnItemClickListener{ parent, view, position, id ->
            when(position) {
                1 -> {
                    val dialog = AlertDialog.Builder(this)
                    dialog.setTitle("로그아웃")
                    dialog.setMessage("로그아웃 하시겠습니까?")
                    dialog.setPositiveButton("예") {dialog, which ->
                        logout()
                    }
                    dialog.setNegativeButton("아니오") {dialog, which ->
                        return@setNegativeButton
                    }
                    dialog.show()
                }
            }
        }

        closeBtn.setOnClickListener { finish() }

        deleteBtn.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("회원탈퇴")
            dialog.setMessage("회원탈퇴 하시겠습니까?")
            dialog.setPositiveButton("예") {dialog, which ->
                deleteUser()
            }
            dialog.setNegativeButton("아니오") {dialog, which ->
                return@setNegativeButton
            }
            dialog.show()
        }
    }

    private fun init() {
        list = findViewById(R.id.info_list)
        closeBtn = findViewById(R.id.info_closeBtn)
        deleteBtn = findViewById(R.id.deleteUser_btn)
    }


    private fun logout() {
        App.prefs.setString("token", "")
        App.prefs.setString("id", "")
        App.prefs.setString("password", "")
        App.prefs.setString("userId", "")
        val intent = Intent(this@UserInfoActivity, LoginActivity::class.java)
        Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
        startActivity(intent)
        finish()
    }

    private fun deleteUser() {
        val token = App.prefs.getString("token", "")
        val id = App.prefs.getString("userId", "")
        RetrofitBuilder.api.deleteUser(token, id.toInt()).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                logout()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("MainActivity!!!", t.message.toString())
            }

        })
    }

}