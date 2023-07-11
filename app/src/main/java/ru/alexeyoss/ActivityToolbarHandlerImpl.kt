package ru.alexeyoss

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager.OnBackStackChangedListener
import ru.alexeyoss.core_ui.presentation.toolbar.FragmentToolbarStateHandler
import ru.alexeyoss.core_ui.presentation.toolbar.ToolbarStates
import ru.alexeyoss.foodie.navigation.MainActivity
import javax.inject.Inject

class ActivityToolbarStateHandler
@Inject constructor(
    private val activity: FragmentActivity, @IdRes private val containerId: Int
) : OnBackStackChangedListener {

    fun registerToolbarStateHandler() {
        activity.supportFragmentManager.addOnBackStackChangedListener(this@ActivityToolbarStateHandler)
    }

    fun removeToolbarStateHandler() {
        activity.supportFragmentManager.removeOnBackStackChangedListener(this@ActivityToolbarStateHandler)
    }

    override fun onBackStackChanged() {
        checkToolbarState()
    }

    private fun checkToolbarState() {
        val currentFragment = activity.supportFragmentManager.findFragmentById(containerId)
        if (currentFragment is FragmentToolbarStateHandler) {
            val toolbarState = currentFragment.getToolbarState()
            updateToolbarView(toolbarState)
        }
    }

    private fun updateToolbarView(toolbarState: ToolbarStates) {
        when (toolbarState) {
            is ToolbarStates.CustomTitle -> setCustomTitleView(toolbarState.title)
            is ToolbarStates.LocationView -> setLocationView()
        }
    }

    private fun setCustomTitleView(toolbarTitle: String) {
        // TODO implement more flexibility without casting
        with((activity as MainActivity).binding) {
            activity.supportActionBar?.apply {
                subtitle = null
                title = toolbarTitle
                setDisplayHomeAsUpEnabled(true)
                profilePhoto.visibility = android.view.View.GONE
            }
        }
    }

    private fun setLocationView() {
        with((activity as MainActivity).binding) {
            activity.supportActionBar?.apply {
                // TODO implement business workflow of location request
                subtitle = "12 августа, 2023"
                title = "Москва"
                setLogo(ru.alexeyoss.core_ui.theme.R.drawable.ic_pinpoint)
                setDisplayHomeAsUpEnabled(false)
                profilePhoto.visibility = android.view.View.VISIBLE
            }
        }
    }
}



