package ru.alexeyoss.foodie.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import com.github.terrakok.cicerone.Forward
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import dagger.Lazy
import kotlinx.coroutines.Dispatchers
import ru.alexeyoss.core_ui.presentation.listeners.BackButtonListener
import ru.alexeyoss.foodie.R
import ru.alexeyoss.foodie.activity.toolbar.MainActivityToolbarHandler
import ru.alexeyoss.foodie.appComponent
import ru.alexeyoss.foodie.coreui.presentation.AlertDialogBuilder
import ru.alexeyoss.foodie.databinding.ActivityMainBinding
import ru.alexeyoss.foodie.navigation.Screens
import ru.alexeyoss.foodie.permissions.LocationPermissionRequest
import ru.alexeyoss.foodie.permissions.LocationPermissionRequest.permissions
import ru.alexeyoss.foodie.permissions.LocationPermissionRequest.showPermissionsRational
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var viewModelFactory: Lazy<MainActivityViewModel.Factory>
    val viewModel by viewModels<MainActivityViewModel> { viewModelFactory.get() }

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val locationPermissionLauncher by lazy {
        registerForActivityResult(RequestMultiplePermissions(), ::onLocationPermissionResult)
    }

    private val toolbarHandler by lazy {
        MainActivityToolbarHandler(
            activity = this@MainActivity,
            containerId = R.id.navHostFragment
        )
    }

    private val navigator = AppNavigator(
        activity = this@MainActivity,
        containerId = R.id.navHostFragment,
        fragmentManager = supportFragmentManager
    )

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this@MainActivity)

        setContentView(binding.root)
        setSupportActionBar(binding.customToolbar)


        Dispatchers
        if (savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.categoriesFragment
            navigator.applyCommands(arrayOf(Forward(Screens.categories())))
        }

        if (!checkPermissionsStatus(permissions)) {
            locationPermissionLauncher.launch(permissions)
        } else {
            viewModel.getLastKnownLocation()
        }

        lifecycle.addObserver(toolbarHandler.lifeCycleObserver)

        initListeners()
    }

    private fun initListeners() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.categoriesFragment -> router.replaceScreen(Screens.categories())
                R.id.searchFragment -> Unit
                R.id.cartFragment -> router.replaceScreen(Screens.cart())
                R.id.accountFragment -> Unit
            }
            true
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        // TODO fix BottomNavigationView Selected State when onBackPressed
        val fragment = supportFragmentManager.findFragmentById(R.id.navHostFragment)
        if (fragment != null && fragment is BackButtonListener && (fragment as BackButtonListener).onBackPressed()) {
            return
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    @SuppressLint("MissingPermission")
    private fun onLocationPermissionResult(result: Map<String, Boolean>) {
        if (result.any { permission -> permission.value }) {
            // Set last known location if ANY of permissions is provided
            viewModel.getLastKnownLocation()
        } else {
            if (shouldShowRequestPermissionRationale(this@MainActivity, permissions.random())) {
                locationPermissionLauncher.launch(permissions)
            } else {
                // Set default city name if ALL permissions is restricted
                viewModel.setDefaultCityName()

                if (showPermissionsRational) {
                    AlertDialogBuilder.createPermissionDialog(
                        this@MainActivity,
                        message = LocationPermissionRequest.permissionsRationalStr,
                        positiveBtnText = R.string.appSettingsBtnText,
                        onPositive = { router.navigateTo(LocationPermissionRequest.settingsRationalRoute!!) },
                    ).show()
                }
            }
        }
    }

    private fun checkPermissionsStatus(permissions: Array<String>): Boolean {
        return permissions.any { permission ->
            ContextCompat.checkSelfPermission(
                this@MainActivity, permission
            ) == PermissionChecker.PERMISSION_GRANTED
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.bottomNavigationView.setOnItemSelectedListener(null)
    }
}
