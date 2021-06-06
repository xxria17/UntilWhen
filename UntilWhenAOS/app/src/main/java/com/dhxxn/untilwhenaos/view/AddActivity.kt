package com.dhxxn.untilwhenaos.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.dhxxn.untilwhenaos.R
import com.dhxxn.untilwhenaos.databinding.ActivityAddBinding
import com.dhxxn.untilwhenaos.databinding.ActivityMainBinding
import com.dhxxn.untilwhenaos.viewmodel.AddViewModel

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    private lateinit var viewModel : AddViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add)
        viewModel = ViewModelProvider(this).get(AddViewModel::class.java)
        binding.add = this
        binding.lifecycleOwner = this
    }
}