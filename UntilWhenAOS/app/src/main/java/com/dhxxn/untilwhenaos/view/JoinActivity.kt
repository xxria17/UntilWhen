package com.dhxxn.untilwhenaos.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.dhxxn.untilwhenaos.R
import com.dhxxn.untilwhenaos.databinding.ActivityJoinBinding
import com.dhxxn.untilwhenaos.model.User
import com.dhxxn.untilwhenaos.network.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoinActivity : AppCompatActivity() {

    private lateinit var binding : ActivityJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)
        binding.join = this

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.joinBtn.setOnClickListener {
            join()
        }

    }

    private fun join() {
        val id = binding.editNewId.text.toString().trim()
        val password = binding.editNewPw.text.toString().trim()
        val passwordCheck = binding.editPwCheck.text.toString().trim()

        if (id.length == 0 || password.length == 0 || passwordCheck.length == 0) {
            Toast.makeText(this, "아이디와 비밀번호를 입력해주세요!", Toast.LENGTH_SHORT).show()
        }

        if (!password.equals(passwordCheck)) {
            Toast.makeText(this, "비밀번호을 다시 확인해주세요!", Toast.LENGTH_SHORT).show()
        }

        val user = HashMap<String, String>()
        user["name"] = id
        user["password"] = password
        RetrofitBuilder.api.getJoinResponse(user).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                Log.d("JoinActivity!!!", "response :: $response")
                startActivity(Intent(this@JoinActivity, LoginActivity::class.java))
                finish()
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("JoinActivity!!!", t.message.toString())
            }

        })
    }
}