package ru.alexeyoss.foodie.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.alexeyoss.foodie.R
import ru.alexeyoss.foodie.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazyUnsafe {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(binding.customToolbar)
    }


    private fun requestLocationPermission() {
        // TODO user activityResultApi
    }

}