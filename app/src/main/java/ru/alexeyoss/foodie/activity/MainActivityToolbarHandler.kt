package ru.alexeyoss.foodie.activity

import android.view.View.GONE
import android.view.View.VISIBLE
import ru.alexeyoss.core_ui.presentation.toolbar.ToolbarHandler
import ru.alexeyoss.core_ui.presentation.toolbar.ToolbarStates
import ru.alexeyoss.core_ui.theme.R.drawable
import ru.alexeyoss.foodie.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivityToolbarHandler
@Inject constructor(
    private val activity: MainActivity,
    containerId: Int
) : ToolbarHandler<MainActivity, ToolbarStates>(activity, containerId) {

    init {
        activity.binding.customToolbar.setNavigationOnClickListener { activity.onBackPressed() }
    }

    override val binding: ActivityMainBinding = activity.binding

    override fun updateToolbarView(toolbarState: ToolbarStates) {
        when (toolbarState) {
            is ToolbarStates.CustomTitle -> setCustomTitleView(toolbarState.title)
            is ToolbarStates.LocationView -> setLocationView()
        }
    }

    private fun setCustomTitleView(toolbarTitle: String) = with(binding) {
        activity.supportActionBar?.apply {
            subtitle = null
            title = toolbarTitle
            setLogo(null)
            setDisplayHomeAsUpEnabled(true)

            profilePhoto.visibility = GONE
        }
    }

    private fun setLocationView() = with(binding) {
        activity.supportActionBar?.apply {
            subtitle = "12 августа, 2023"
            title = "Москва"
            setLogo(drawable.ic_pinpoint)
            setDisplayHomeAsUpEnabled(false)
            profilePhoto.visibility = VISIBLE
        }
    }

}



