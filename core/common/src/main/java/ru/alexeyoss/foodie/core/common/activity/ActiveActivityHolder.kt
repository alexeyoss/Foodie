package ru.alexeyoss.foodie.core.common.activity

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import javax.inject.Inject

class ActiveActivityHolder
@Inject constructor() : DefaultLifecycleObserver {

    var activity: FragmentActivity? = null

    /**
     * Check does activity exist
     * */
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
