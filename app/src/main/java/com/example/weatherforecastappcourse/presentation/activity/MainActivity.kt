package com.example.weatherforecastappcourse.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherforecastappcourse.R
import com.example.weatherforecastappcourse.presentation.fragments.MainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.placeholder, MainFragment.newInstance())
            .commit()
    }
}