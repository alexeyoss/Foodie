package ru.alexeyoss.foodie.activity.toolbar

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.MutableLiveData
import ru.alexeyoss.core_ui.presentation.collectOnLifecycle
import ru.alexeyoss.core_ui.presentation.toolbar.FoodieToolbarStates
import ru.alexeyoss.core_ui.presentation.toolbar.ToolbarHandler
import ru.alexeyoss.core_ui.theme.R.drawable
import ru.alexeyoss.foodie.activity.MainActivity
import ru.alexeyoss.foodie.activity.domain.entities.UiLocationInfo
import ru.alexeyoss.foodie.databinding.ActivityMainBinding
import java.util.Calendar
import javax.inject.Inject

class MainActivityToolbarHandler
@Inject constructor(
    private val activity: MainActivity, containerId: Int
) : ToolbarHandler<MainActivity, FoodieToolbarStates>(activity, containerId) {

    init {
        activity.binding.customToolbar.setNavigationOnClickListener {
            activity.onBackPressed()
        }

        initToolbarListeners()
    }

    private val lastDate = MutableLiveData<String>()
        get() {
            if (field.value == null) {
                field.value = Calendar.getInstance().time.toString()
            }
            return field
        }

    private val locationData = MutableLiveData<UiLocationInfo>()

    private fun initToolbarListeners() {

        activity.viewModel.toolbarLocationStateFlow.collectOnLifecycle(activity) { locationUiStates ->
            when (locationUiStates) {
                is MainActivityLocationUiStates.Error -> Unit
                is MainActivityLocationUiStates.Initial -> Unit
                is MainActivityLocationUiStates.Loading -> Unit
                is MainActivityLocationUiStates.Success -> {
                    setCityName(locationUiStates.uiLocationInfo)
                    locationData.value = locationUiStates.uiLocationInfo
                }
            }
        }
    }

    private fun setCityName(uiLocationInfo: UiLocationInfo) {
        activity.supportActionBar?.apply {
            title = uiLocationInfo.cityName
        }
    }

    override val binding: ActivityMainBinding = activity.binding

    override fun updateToolbarView(toolbarState: FoodieToolbarStates) {
        when (toolbarState) {
            is FoodieToolbarStates.CustomTitle -> setCustomTitleView(toolbarState.title)
            is FoodieToolbarStates.LocationView -> setLocationView()
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
            subtitle = lastDate.value
            title = locationData.value?.cityName ?: ""

            setLogo(drawable.ic_pinpoint)
            setDisplayHomeAsUpEnabled(false)
            profilePhoto.visibility = VISIBLE
        }
    }
}



