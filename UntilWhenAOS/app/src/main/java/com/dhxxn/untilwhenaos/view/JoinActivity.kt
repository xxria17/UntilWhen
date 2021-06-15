package com.dhxxn.untilwhenaos.view

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.dhxxn.untilwhenaos.KeyboardVisibilityUtils
import com.dhxxn.untilwhenaos.R
import com.dhxxn.untilwhenaos.databinding.ActivityJoinBinding
import com.dhxxn.untilwhenaos.viewmodel.JoinViewModel
import com.dhxxn.untilwhenaos.viewmodel.LoginViewModel

class JoinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJoinBinding
    private lateinit var keyboardVisibilityUtils: KeyboardVisibilityUtils
    private lateinit var viewModel : JoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)
        viewModel = ViewModelProvider(this).get(JoinViewModel::class.java)
        binding.join = this
        binding.lifecycleOwner = this
        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.joinBtn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("회원가입")
            builder.setMessage("이렇게 회원가입을 진행할까요?")

            val id = binding.editNewId.text.toString().trim()
            val password = binding.editNewPw.text.toString().trim()
            val passwordCheck = binding.editPwCheck.text.toString().trim()
            builder.setPositiveButton("예") { dialog, which ->
                viewModel.join(id, password, passwordCheck, this)
            }
            builder.setNegativeButton("아니오") { dialog, which ->
                return@setNegativeButton
            }
            builder.show()
        }

        keyboardVisibilityUtils = KeyboardVisibilityUtils(window,
                onShowKeyboard = { keyboardHeight ->
                    binding.joinScrollView.run {
                        smoothScrollTo(scrollX, scrollY + keyboardHeight)
                    }
                })

    }
}