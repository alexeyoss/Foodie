package ru.alexeyoss.core.common

import android.app.Activity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class ActiveActivityHolder {

    var activity: Activity? = null

    val activityFlow: Flow<Activity> = listOfNotNull(activity).asFlow()

    val isExist: Boolean
        get() = activity != null

    fun clearActivity() {
        this.activity = null
    }
}
