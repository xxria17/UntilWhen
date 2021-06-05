package com.dhxxn.untilwhenaos.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.dhxxn.untilwhenaos.KeyboardVisibilityUtils
import com.dhxxn.untilwhenaos.R
import com.dhxxn.untilwhenaos.databinding.ActivityJoinBinding
import com.dhxxn.untilwhenaos.model.User
import com.dhxxn.untilwhenaos.network.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class JoinActivity : AppCompatActivity() {

    private lateinit var binding : ActivityJoinBinding
    private lateinit var keyboardVisibilityUtils: KeyboardVisibilityUtils

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

        keyboardVisibilityUtils = KeyboardVisibilityUtils(window,
        onShowKeyboard = {keyboardHeight ->
            binding.joinScrollView.run {
                smoothScrollTo(scrollX, scrollY + keyboardHeight)
            }
        })

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

        if (password.length < 6) {
            Toast.makeText(this, "비밀번호는 6자 이상으로 해주세요!", Toast.LENGTH_SHORT).show()
        }

        if (checkType(id)) {
            Toast.makeText(this, "아이디는 영어 대문자, 소문자, 숫자로만 구성할 수 있어요!", Toast.LENGTH_SHORT).show()
        }

        val user = HashMap<String, String>()
        user["name"] = id
        user["password"] = password
        RetrofitBuilder.api.getJoinResponse(user).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                Log.d("JoinActivity!!!", "response :: $response")
                val error = response.errorBody()!!.string()
                if (error == "300") {
                    Toast.makeText(this@JoinActivity, "이미 있는 아이디입니다.", Toast.LENGTH_SHORT).show()
                } else {
                    startActivity(Intent(this@JoinActivity, LoginActivity::class.java))
                    finish()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@JoinActivity, "서버에 연결이 되지 않았습니다. 다시 시도해주세요!", Toast.LENGTH_SHORT).show()
                Log.e("JoinActivity!!!", t.message.toString())
            }

        })
    }

    private fun checkType(word : String) : Boolean {
        return Pattern.matches("^[가-힣]*$", word)
    }
}