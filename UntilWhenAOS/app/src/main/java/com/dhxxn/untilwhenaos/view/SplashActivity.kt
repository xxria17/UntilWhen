package com.dhxxn.untilwhenaos.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.dhxxn.untilwhenaos.R

class SplashActivity : AppCompatActivity() {
    internal val handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        handler.postDelayed({
            val intent = Intent(baseContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)
    }
}