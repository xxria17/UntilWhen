package com.dhxxn.untilwhenaos.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.dhxxn.untilwhenaos.App
import com.dhxxn.untilwhenaos.PreferenceUtil
import com.dhxxn.untilwhenaos.network.RetrofitBuilder
import com.dhxxn.untilwhenaos.view.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(application: Application): AndroidViewModel(application) {

    fun login(name : String, password : String, context: Context) {
        if(validation(name, password, context)) {
            val user = HashMap<String, String>()
            user["name"] = name
            user["password"] = password

            RetrofitBuilder.api.getLoginResponse(user).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.body() != null) {
                        val intent = Intent(context, MainActivity::class.java)
                        App.prefs.setString("token", response.body()!!)
                        App.prefs.setString("id", name)
                        App.prefs.setString("password", password)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        context.startActivity(intent)
                    } else {
                        val error = response.errorBody()!!.string()
                        if (error == "100") {
                            Toast.makeText(context, "가입되지 않은 사용자입니다.", Toast.LENGTH_SHORT).show()
                        } else if (error == "200") {
                            Toast.makeText(context, "잘못된 비밀번호 입니다.", Toast.LENGTH_SHORT).show()
                        }
                    }

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(context, "서버에 연결이 되지 않았습니다. 다시 시도해주세요!", Toast.LENGTH_SHORT).show()
                    Log.e("LoginActivity!!!", t.message.toString())
                }
            })
        }
    }


    private fun validation(id : String, password: String, context: Context): Boolean {
        if (id.length == 0 || password.length == 0) {
            Toast.makeText(context, "아이디와 비밀번호를 입력해주세요!", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}