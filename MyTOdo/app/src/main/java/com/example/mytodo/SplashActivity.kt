package com.example.mytodo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {  // Use override
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        val i = Intent(this, MainActivity::class.java)
        Handler(Looper.getMainLooper()).postDelayed({
            Log.d("SplashActivity", "Launching MainActivity")
            startActivity(i)
            finish()
        }, 1000)
    }
}
