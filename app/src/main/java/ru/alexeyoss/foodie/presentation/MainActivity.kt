package ru.alexeyoss.foodie.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.alexeyoss.foodie.R
import ru.alexeyoss.foodie.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazyUnsafe {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val navController by lazy {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navHostFragment.navController
    }
    private val appbarConfiguration = AppBarConfiguration(navGraph = navController.graph)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO maybe get rid of classic toolbar
        setSupportActionBar(binding.customToolbar)
        initNavComponent()
    }

    private fun initNavComponent() {
        val navView = binding.bottomNavigationView
        setupActionBarWithNavController(navController, appbarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun requestLocationPermission() {
        // TODO user activityResultApi
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appbarConfiguration) || super.onSupportNavigateUp()
    }
}