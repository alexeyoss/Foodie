package ru.alexeyoss.foodie

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.alexeyoss.core.presentation.ToolbarStates
import ru.alexeyoss.core.presentation.ToolbarTitleHandler
import ru.alexeyoss.foodie.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

//    private val navComponentRouter = NavComponentRouterImpl(this, R.id.navHostFragment)

    private val navController by lazy {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navHostFragment.navController
    }

    private lateinit var appBarConfiguration: AppBarConfiguration

    private val currentFragment: Fragment
        get() = supportFragmentManager.findFragmentById(R.id.navHostFragment)!!

    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            updateToolbarTitle()
        }
    }

    private val locationPermissionsLauncher = registerForActivityResult(
        RequestMultiplePermissions(), ::onPermissionsResult
    )

    private val permissionList =
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(binding.customToolbar)

        locationPermissionsLauncher.launch(permissionList)

        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, true)


        appBarConfiguration = AppBarConfiguration(navGraph = navController.graph)
        initNavComponent()
    }

    private fun initNavComponent() {
        val navView = binding.bottomNavigationView
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }


    private fun onPermissionsResult(grantResult: Map<String, Boolean>) {
        if (grantResult.all { permission -> permission.value }) {
            // TODO set permission location to toolbar
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                locationPermissionsLauncher.launch(permissionList)
            } else {
                // TODO Set default value to User location into Toolbar
            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        // TODO NavComponentRouterImpl.onNavigateUp(appBarConfiguration)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    private fun updateToolbarTitle() {
        val fragment = currentFragment

        if (fragment is ToolbarTitleHandler) {

            when (fragment.getToolbarState()) {
                ToolbarStates.LOCATION -> {

                }

                ToolbarStates.CLEAR_TITLE -> {

                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
    }
}