package ru.alexeyoss.core.common.activity

import androidx.activity.ComponentActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

class ActiveActivityHolder
@Inject constructor() : DefaultLifecycleObserver {

    var activity: FragmentActivity? = null

    val activityFlow: Flow<FragmentActivity> = listOfNotNull(activity).asFlow()

    val isActivityExist: Boolean
        get() = activity != null

    /**
     * Register link on active activity instance
     * */
    fun registerActiveActivity(newActivity: FragmentActivity) {
        this.activity = newActivity
    }

    /**
     * Remove link on active activity instance
     * */
    fun removeActiveActivity() {
        this.activity = null
    }

}
