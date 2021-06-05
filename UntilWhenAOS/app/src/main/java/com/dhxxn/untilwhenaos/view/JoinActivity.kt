package com.dhxxn.untilwhenaos.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dhxxn.untilwhenaos.KeyboardVisibilityUtils
import com.dhxxn.untilwhenaos.R
import com.dhxxn.untilwhenaos.databinding.ActivityJoinBinding
import com.dhxxn.untilwhenaos.viewmodel.JoinViewModel

class JoinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJoinBinding
    private lateinit var keyboardVisibilityUtils: KeyboardVisibilityUtils
    private var viewModel = JoinViewModel(application)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)
        binding.join = this

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.joinBtn.setOnClickListener {
            val id = binding.editNewId.text.toString().trim()
            val password = binding.editNewPw.text.toString().trim()
            val passwordCheck = binding.editPwCheck.text.toString().trim()
            viewModel.join(id, password, passwordCheck, this)
            finish()
        }

        keyboardVisibilityUtils = KeyboardVisibilityUtils(window,
                onShowKeyboard = { keyboardHeight ->
                    binding.joinScrollView.run {
                        smoothScrollTo(scrollX, scrollY + keyboardHeight)
                    }
                })

    }
}