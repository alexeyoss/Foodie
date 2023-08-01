package ru.alexeyoss.foodie.activity.toolbar

import android.location.Location
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
import ru.alexeyoss.foodie.databinding.ActivityMainBinding
import java.util.Calendar
import javax.inject.Inject

// TODO Refactor
class MainActivityToolbarHandler
@Inject constructor(
    private val activity: MainActivity, containerId: Int
) : ToolbarHandler<MainActivity, ToolbarStates>(activity, containerId) {

    init {
        activity.binding.customToolbar.setNavigationOnClickListener {
            @Suppress("DEPRECATION")
            activity.onBackPressed()
        }
    }

    private val locationData = MutableLiveData<Pair<String, String>>()

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
        activity.viewModel.toolbarLocationStateFlow.collectOnLifecycle(activity) { mainActivityUiStates ->
            when (mainActivityUiStates) {
                is MainActivityLocationUiStates.Error -> Unit
                is MainActivityLocationUiStates.Initial -> Unit
                is MainActivityLocationUiStates.Loading -> Unit
                is MainActivityLocationUiStates.Success -> {
                    locationData.value = formatTitleAnSubtitle(mainActivityUiStates.location)
                }
            }
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

    private fun setLocationView(newLocation: Pair<String, String>? = null) = with(binding) {
        activity.supportActionBar?.apply {
            title = locationData.value?.first ?: newLocation?.first
            subtitle = locationData.value?.second ?: newLocation?.second

            setLogo(drawable.ic_pinpoint)
            setDisplayHomeAsUpEnabled(false)
            profilePhoto.visibility = VISIBLE
        }
    }


    private fun formatTitleAnSubtitle(location: Location): Pair<String, String> {
        val title = location.longitude.toString() + " " + location.latitude.toString()
        val subtitle = Calendar.getInstance().time.toString()
        return title to subtitle
    }

}



