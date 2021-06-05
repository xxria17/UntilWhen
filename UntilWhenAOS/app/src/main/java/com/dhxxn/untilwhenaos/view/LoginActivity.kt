package com.dhxxn.untilwhenaos.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.dhxxn.untilwhenaos.R
import com.dhxxn.untilwhenaos.databinding.ActivityLoginBinding
import com.dhxxn.untilwhenaos.model.User
import com.dhxxn.untilwhenaos.network.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.login = this

        binding.loginBtn.setOnClickListener {
            login()
        }

        binding.goJoinBtn.setOnClickListener {
            startActivity(Intent(this@LoginActivity, JoinActivity::class.java))
        }
    }

    private fun login() {
        val id = binding.editId.text.toString().trim()
        val password = binding.editPassword.text.toString().trim()

        if (id.length == 0 || password.length == 0) {
            Toast.makeText(this, "아이디와 비밀번호를 입력해주세요!", Toast.LENGTH_SHORT).show()
            return
        }

        val user = HashMap<String, String>()
        user["name"] = id
        user["password"] = password
        var api = RetrofitBuilder.api.getLoginResponse(user).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d("LoginActivity!!!", "response :: ${response.message()} !! ${response.code()} !! ${response.body()}")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("LoginActivity!!!", t.message.toString())
            }
        })
    }
}