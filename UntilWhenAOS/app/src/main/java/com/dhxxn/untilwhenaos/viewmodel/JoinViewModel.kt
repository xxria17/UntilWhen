package com.dhxxn.untilwhenaos.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.dhxxn.untilwhenaos.App
import com.dhxxn.untilwhenaos.model.User
import com.dhxxn.untilwhenaos.network.RetrofitBuilder
import com.dhxxn.untilwhenaos.view.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class JoinViewModel(application: Application): AndroidViewModel(application) {

    fun join(id : String, password : String, passwordCheck: String, context: Context){

        if (validation(id, password, passwordCheck, context)) {
            val user = HashMap<String, String>()
            user["name"] = id
            user["password"] = password
            RetrofitBuilder.api.getJoinResponse(user).enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    Log.d("JoinActivity!!!", "response :: $response")
                    if (response.errorBody() != null) {
                        val error = response.errorBody()!!.string()
                        if (error == "300") {
                            Toast.makeText(context, "이미 있는 아이디입니다.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        App.prefs.setString("id", "")
                        App.prefs.setString("password", "")
                        Toast.makeText(context, "회원가입 되었습니다.", Toast.LENGTH_SHORT).show()
                        context.startActivity(Intent(context, LoginActivity::class.java))
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(context, "서버에 연결이 되지 않았습니다. 다시 시도해주세요!", Toast.LENGTH_SHORT).show()
                    Log.e("JoinActivity!!!", t.message.toString())
                }

            })
        }


    }

    private fun validation(id : String, password : String, passwordCheck: String, context: Context): Boolean {

        if (id.length == 0 || password.length == 0 || passwordCheck.length == 0) {
            Toast.makeText(context, "아이디와 비밀번호를 입력해주세요!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!password.equals(passwordCheck)) {
            Toast.makeText(context, "비밀번호을 다시 확인해주세요!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.length < 6) {
            Toast.makeText(context, "비밀번호는 6자 이상으로 해주세요!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (checkType(id)) {
            Toast.makeText(context, "아이디는 영어 대문자, 소문자, 숫자로만 구성할 수 있어요!", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }


    private fun checkType(word : String) : Boolean {
        return Pattern.matches("^[가-힣]*$", word)
    }
}