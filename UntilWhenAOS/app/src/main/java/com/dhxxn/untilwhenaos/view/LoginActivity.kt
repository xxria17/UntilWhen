package com.dhxxn.untilwhenaos.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.dhxxn.untilwhenaos.KeyboardVisibilityUtils
import com.dhxxn.untilwhenaos.R
import com.dhxxn.untilwhenaos.databinding.ActivityLoginBinding
import com.dhxxn.untilwhenaos.network.RetrofitBuilder
import com.dhxxn.untilwhenaos.viewmodel.LoginViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var keyboardVisibilityUtils: KeyboardVisibilityUtils
    private var viewModel = LoginViewModel(application)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.login = this

        binding.loginBtn.setOnClickListener {
            val id = binding.editId.text.toString().trim()
            val password = binding.editPassword.text.toString().trim()

            viewModel.login(id ,password, this)
        }

        binding.goJoinBtn.setOnClickListener {
            startActivity(Intent(this@LoginActivity, JoinActivity::class.java))
        }

        //  키보드 스크롤
        keyboardVisibilityUtils = KeyboardVisibilityUtils(window,
        onShowKeyboard = { keyboardHeight ->
            binding.loginScrollView.run {
                smoothScrollTo(scrollX, scrollY + keyboardHeight)
            }
        })
    }

}