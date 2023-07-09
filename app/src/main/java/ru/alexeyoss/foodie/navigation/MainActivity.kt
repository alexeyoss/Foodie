package ru.alexeyoss.foodie.navigation

import android.Manifest
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentManager
import com.github.terrakok.cicerone.Forward
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import ru.alexeyoss.core.common.BackButtonListener
import ru.alexeyoss.core.presentation.ToolbarStateHandler
import ru.alexeyoss.core.presentation.ToolbarStates
import ru.alexeyoss.foodie.R
import ru.alexeyoss.foodie.appComponent
import ru.alexeyoss.foodie.databinding.ActivityMainBinding
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val navigator = AppNavigator(
        activity = this,
        containerId = R.id.navHostFragment,
        fragmentManager = supportFragmentManager
    )

    private val locationPermissionsLauncher = registerForActivityResult(
        RequestMultiplePermissions(), ::onPermissionsResult
    )

    private val permissionList =
        arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

    private val backStackListener = FragmentManager.OnBackStackChangedListener { checkToolbarState() }

    private fun checkToolbarState() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment)!!
        if (currentFragment is ToolbarStateHandler) {
            val toolbarState = currentFragment.getToolbarState()
            updateToolbarView(toolbarState)
        }
    }

    private fun updateToolbarView(toolbarState: ToolbarStates) = with(binding) {
        when (toolbarState) {
            is ToolbarStates.CustomTitle -> {
                supportActionBar?.apply {
                    subtitle = ""
                    title = toolbarState.title
                    setIcon(ru.alexeyoss.core.theme.R.drawable.ic_pinpoint)
                }

            }

            is ToolbarStates.LocationView -> {
                binding.customToolbar.logo.setVisible(true, false)
                supportActionBar?.apply {
                    subtitle = "12 августа, 2023"
                    title = "Москва"
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this@MainActivity)

        setContentView(binding.root)
        setSupportActionBar(binding.customToolbar)

        if (savedInstanceState == null) {
            navigator.applyCommands(arrayOf(Forward(Screens.categories())))
        }
        locationPermissionsLauncher.launch(permissionList)
        initListeners()
    }

    // TODO Remove after debugging
    private fun fragmentsStackListener() {
        supportFragmentManager.addOnBackStackChangedListener {
            Timber.tag("FRAG").i(
                "${supportFragmentManager.backStackEntryCount} - ${
                    supportFragmentManager.findFragmentById(
                        R.id.navHostFragment
                    )
                }"
            )
        }
    }

    private fun initListeners() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.categoriesFragment -> router.newRootScreen(Screens.categories())
                R.id.searchFragment -> Unit
                R.id.cartFragment -> router.newRootScreen(Screens.cart())
                R.id.accountFragment -> Unit
            }
            true
        }

        supportFragmentManager.addOnBackStackChangedListener(backStackListener)

        // TODO Remove after debugging
        fragmentsStackListener()
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
        val fragment = supportFragmentManager.findFragmentById(R.id.navHostFragment)
        if (fragment != null && fragment is BackButtonListener && (fragment as BackButtonListener).onBackPressed()) {
            return
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    // TODO extract logic to [ViewModel] -> [PermissionManager]
    private fun onPermissionsResult(grantResult: Map<String, Boolean>) {
        if (grantResult.all { permission -> permission.value }) {
            // TODO set permission location to toolbar
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                locationPermissionsLauncher.launch(permissionList)
            } else {
                // TODO Set default value to User location into Toolbar
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.removeOnBackStackChangedListener(backStackListener)
        binding.bottomNavigationView.setOnItemSelectedListener(null)
    }
}