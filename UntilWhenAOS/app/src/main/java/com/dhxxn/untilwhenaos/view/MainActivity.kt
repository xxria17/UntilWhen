package com.dhxxn.untilwhenaos.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhxxn.untilwhenaos.R
import com.dhxxn.untilwhenaos.databinding.ActivityMainBinding
import com.dhxxn.untilwhenaos.viewmodel.LoginViewModel
import com.dhxxn.untilwhenaos.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.lifecycleOwner = this
        binding.main = viewModel

        binding.mainDdayList.layoutManager = LinearLayoutManager(this)
        val token = intent.getStringExtra("token") ?: ""
        viewModel.setInit(this, binding.mainDdayList, token)

        binding.mainAddBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddActivity::class.java))
        }

    }
}