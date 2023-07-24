package ru.alexeyoss.foodie.activity

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.github.terrakok.cicerone.Forward
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import dagger.Lazy
import ru.alexeyoss.core_ui.presentation.AlertDialogBuilder
import ru.alexeyoss.core_ui.presentation.BackButtonListener
import ru.alexeyoss.foodie.R
import ru.alexeyoss.foodie.appComponent
import ru.alexeyoss.foodie.databinding.ActivityMainBinding
import ru.alexeyoss.foodie.navigation.Screens
import ru.alexeyoss.foodie.permission.LocationPermissionRequest
import ru.alexeyoss.foodie.permission.LocationPermissionRequest.permissions
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    @Inject
    internal lateinit var viewModelFactory: Lazy<MainActivityViewModel.Factory>
    private val viewModel by viewModels<MainActivityViewModel> { viewModelFactory.get() }

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val toolbarHandler by lazy {
        MainActivityToolbarHandler(
            activity = this@MainActivity, containerId = R.id.navHostFragment
        )
    }

    private val locationPermissionLauncher by lazy {
        registerForActivityResult(RequestMultiplePermissions(), ::onLocationPermissionResult)
    }

    private val navigator = AppNavigator(
        activity = this@MainActivity, containerId = R.id.navHostFragment, fragmentManager = supportFragmentManager
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this@MainActivity)

        setContentView(binding.root)
        setSupportActionBar(binding.customToolbar)


        if (savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.categoriesFragment
            navigator.applyCommands(arrayOf(Forward(Screens.categories())))
        }

        locationPermissionLauncher.launch(permissions)

        registerToolbarHandler()
        initListeners()
    }

    private fun registerToolbarHandler() {
        lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                toolbarHandler.addToolbarStateListener()
            }

            override fun onDestroy(owner: LifecycleOwner) {
                toolbarHandler.removeToolbarStateListener()
            }
        })
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

    // HelpMe is it correct to build independent Permission Manager ?
    private fun onLocationPermissionResult(result: Map<String, Boolean>) {
        if (result.all { permission -> permission.value }) {
            // TODO start location service  OR set locationInfo into Toolbar
        } else {
            if (shouldShowRequestPermissionRationale(
                    this@MainActivity, permissions.first()
                )
            ) {
                locationPermissionLauncher.launch(permissions)
            } else {
                // TODO set default position
                if (LocationPermissionRequest.showPermissionsRational) {
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

    override fun onDestroy() {
        super.onDestroy()
        binding.bottomNavigationView.setOnItemSelectedListener(null)
    }
}
