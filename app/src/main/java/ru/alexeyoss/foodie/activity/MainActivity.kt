package ru.alexeyoss.foodie.activity

import android.Manifest
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.github.terrakok.cicerone.Forward
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import dagger.Lazy
import ru.alexeyoss.core.common.activity.DefaultActivityLifecycleCallbacks
import ru.alexeyoss.core_ui.presentation.BackButtonListener
import ru.alexeyoss.foodie.R
import ru.alexeyoss.foodie.appComponent
import ru.alexeyoss.foodie.databinding.ActivityMainBinding
import ru.alexeyoss.foodie.navigation.Screens
import ru.alexeyoss.services.permission.PermissionManager
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    @Inject
    internal lateinit var viewModelFactory: Lazy<MainActivityViewModel.Factory>

    private val viewModel by viewModels<MainActivityViewModel> {
        viewModelFactory.get()
    }

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var permissionManager: PermissionManager


    private val toolbarHandler by lazy {
        MainActivityToolbarHandler(
            activity = this@MainActivity, containerId = R.id.navHostFragment
        )
    }

    private val navigator = AppNavigator(
        activity = this@MainActivity, containerId = R.id.navHostFragment, fragmentManager = supportFragmentManager
    )

    private val locationPermissionsLauncher = registerForActivityResult(
        RequestMultiplePermissions(), ::onPermissionsResult
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

        registerToolbarHandler()
//        locationPermissionsLauncher.launch(permissionList)
        initListeners()
    }

    private fun registerToolbarHandler() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            registerActivityLifecycleCallbacks(object : DefaultActivityLifecycleCallbacks {
                override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
                    toolbarHandler.addToolbarStateListener()
                }

                override fun onActivityDestroyed(activity: Activity) {
                    toolbarHandler.removeToolbarStateListener()
                }
            })
        } else {
            lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onCreate(owner: LifecycleOwner) {
                    toolbarHandler.addToolbarStateListener()
                }

                override fun onDestroy(owner: LifecycleOwner) {
                    toolbarHandler.removeToolbarStateListener()
                }
            })
        }
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

        viewModel.permStatus.observe(this@MainActivity) {
            Toast.makeText(this@MainActivity, "asdfsadfas", Toast.LENGTH_SHORT).show()
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

    // TODO extract logic to [ViewModel] -> [PermissionManager]
    private val permissionList = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    )

    // TODO extract logic to [ViewModel] -> [PermissionManager]
    private fun onPermissionsResult(grantResult: Map<String, Boolean>) {
        if (grantResult.all { permission -> permission.value }) {
            // TODO set permission location to toolbar
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.ACCESS_FINE_LOCATION
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
        binding.bottomNavigationView.setOnItemSelectedListener(null)
    }
}