package ru.alexeyoss.foodie.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.cicerone.Forward
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import dagger.Lazy
import ru.alexeyoss.core_ui.presentation.collectOnLifecycle
import ru.alexeyoss.core_ui.presentation.listeners.BackButtonListener
import ru.alexeyoss.foodie.R
import ru.alexeyoss.foodie.activity.toolbar.MainActivityToolbarHandler
import ru.alexeyoss.foodie.appComponent
import ru.alexeyoss.foodie.core.common.activity.ActiveActivityHolder
import ru.alexeyoss.foodie.coreui.presentation.AlertDialogBuilder
import ru.alexeyoss.foodie.databinding.ActivityMainBinding
import ru.alexeyoss.foodie.navigation.Screens
import ru.alexeyoss.foodie.permissions.request.LocationPermissionRequest
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

    @Inject
    lateinit var activeActivityHolder: ActiveActivityHolder

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
        activeActivityHolder.activity = this@MainActivity

        setContentView(binding.root)
        setSupportActionBar(binding.customToolbar)

        if (savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.categoriesFragment
            navigator.applyCommands(arrayOf(Forward(Screens.categories())))
        }

        viewModel.checkPermissionStatus()

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

        viewModel.mainActivitySideEffects.collectOnLifecycle(this@MainActivity) { sideEffect ->
            when (sideEffect) {
                MainActivitySideEffects.Initial -> Unit
                MainActivitySideEffects.ShowPermissionRational ->
                    AlertDialogBuilder.createPermissionDialog(
                        this@MainActivity,
                        message = LocationPermissionRequest.permissionsRationalStr,
                        positiveBtnText = R.string.appSettingsBtnText,
                        onPositive = { router.navigateTo(LocationPermissionRequest.settingsRationalRoute!!) },
                    ).show()
            }
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // Delegate result to permission manager via viewModel method
        viewModel.onPermissionResult(requestCode, permissions, grantResults)
    }

    override fun onDestroy() {
        super.onDestroy()
        activeActivityHolder.activity = null
        binding.bottomNavigationView.setOnItemSelectedListener(null)
    }
}
