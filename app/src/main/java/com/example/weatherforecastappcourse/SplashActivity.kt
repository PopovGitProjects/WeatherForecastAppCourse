package com.example.weatherforecastappcourse

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherforecastappcourse.databinding.ActivitySplashBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sideAnimation = AnimationUtils.loadAnimation(this, R.anim.logo_anim)
        binding.imgSplashLogo.startAnimation(sideAnimation)

        CoroutineScope(Dispatchers.IO).launch {
            delay(3000)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        }

    }

    override fun onRestart() {
        super.onRestart()
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
    }
}