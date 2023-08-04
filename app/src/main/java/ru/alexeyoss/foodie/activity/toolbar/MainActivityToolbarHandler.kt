package ru.alexeyoss.foodie.activity.toolbar

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import ru.alexeyoss.core_ui.presentation.collectOnLifecycle
import ru.alexeyoss.core_ui.presentation.toolbar.ToolbarHandler
import ru.alexeyoss.core_ui.presentation.toolbar.ToolbarStates
import ru.alexeyoss.core_ui.theme.R.drawable
import ru.alexeyoss.foodie.activity.MainActivity
import ru.alexeyoss.foodie.activity.domain.entities.UiLocationInfo
import ru.alexeyoss.foodie.databinding.ActivityMainBinding
import java.util.Calendar
import javax.inject.Inject

class MainActivityToolbarHandler
@Inject constructor(
    private val activity: MainActivity, containerId: Int
) : ToolbarHandler<MainActivity, ToolbarStates>(activity, containerId) {

    init {
        activity.binding.customToolbar.setNavigationOnClickListener {
            activity.onBackPressed()
        }
    }

    private val lastDate: String? = null
        get() = field ?: Calendar.getInstance().time.toString()


    private val locationData = MutableLiveData<UiLocationInfo>()

    override val lifeCycleObserver: DefaultLifecycleObserver = object : DefaultLifecycleObserver {
        override fun onCreate(owner: LifecycleOwner) {
            addToolbarStateListener()
            initToolbarListeners()
        }

        override fun onDestroy(owner: LifecycleOwner) {
            removeToolbarStateListener()
        }
    }

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
            subtitle = lastDate
            title = locationData.value?.cityName ?: ""

            setLogo(drawable.ic_pinpoint)
            setDisplayHomeAsUpEnabled(false)
            profilePhoto.visibility = VISIBLE
        }
    }
}



