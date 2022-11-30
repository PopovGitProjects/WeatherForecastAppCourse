package com.example.weatherforecastappcourse

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherforecastappcourse.fragments.MainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.placeholder, MainFragment.newInstance())
            .commit()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.weather_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.itmConv -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.placeholder, ConverterFragment.newInstance())
                    .addToBackStack("key")
                    .commit()
                Toast.makeText(this, "Button click!", Toast.LENGTH_SHORT).show()
            }
            R.id.itmSet -> {
                Toast.makeText(this, "Button click!", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }
}