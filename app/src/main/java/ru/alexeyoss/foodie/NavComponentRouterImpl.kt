package ru.alexeyoss.foodie

import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import ru.alexeyoss.core.presentation.ARG_SCREEN
import javax.inject.Inject

class NavComponentRouterImpl
@Inject constructor(
    private val activity: AppCompatActivity,
    @IdRes private val fragmentContainerId: Int,
) : NavComponentRouter {


    override fun launchScreen(
        @IdRes destinationId: Int, arg: Parcelable?
    ) {
        if (arg == null) {
            getRootNavController().navigate(resId = destinationId)
        } else {
            getRootNavController().navigate(
                resId = destinationId,
                args = Bundle(1).apply {
                    putParcelable(ARG_SCREEN, arg)
                })
        }
    }

    override fun goBack() {
        activity.onBackPressedDispatcher.onBackPressed()
    }

    private fun getRootNavController(): NavController {
        val fragmentManager = activity.supportFragmentManager
        val navHost = fragmentManager.findFragmentById(fragmentContainerId) as NavHostFragment
        return navHost.navController
    }

    fun onNavigateUp(appBarConfiguration: AppBarConfiguration): Boolean {
        return getRootNavController().navigateUp(appBarConfiguration)
    }

}