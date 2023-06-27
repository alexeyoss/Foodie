package ru.alexeyoss.foodie.navigation

import android.Manifest
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Forward
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import dagger.hilt.android.AndroidEntryPoint
import ru.alexeyoss.core.common.BackButtonListener
import ru.alexeyoss.foodie.R
import ru.alexeyoss.foodie.databinding.ActivityMainBinding
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val navigator = object : AppNavigator(
        activity = this, containerId = R.id.navHostFragment, fragmentManager = supportFragmentManager
    ) {
        override fun applyCommands(commands: Array<out Command>) {
            super.applyCommands(commands)
            supportFragmentManager.executePendingTransactions()
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

        if (savedInstanceState == null) {
            navigator.applyCommands(arrayOf<Command>(Forward(Screens.categories())))
        }
        locationPermissionsLauncher.launch(permissionList)
        initListeners()

        // TODO Remove after debugging
        fragmentsStackListener()
    }

    // TODO Remove after debugging
    private fun fragmentsStackListener() {
        supportFragmentManager.addOnBackStackChangedListener {
            Timber.tag("FRAG")
                .i("${supportFragmentManager.backStackEntryCount} - ${supportFragmentManager.findFragmentById(R.id.navHostFragment)}")
        }
    }

    private fun initListeners() {
        // TODO Debug callback isn't not working
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.categoriesFragment -> {
                    router.navigateTo(Screens.categories())
                    true
                }

                R.id.searchFragment -> true
                R.id.cartFragment -> {
                    router.navigateTo(Screens.cart())
                    true
                }

                R.id.accountFragment -> true
                else -> false
            }
        }

    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.navHostFragment)
        if (fragment != null && fragment is BackButtonListener && (fragment as BackButtonListener).onBackPressed()) {
            return
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
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
}