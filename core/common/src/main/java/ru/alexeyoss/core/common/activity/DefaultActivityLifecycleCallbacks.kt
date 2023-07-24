package ru.alexeyoss.core.common.activity

import android.app.Activity
import android.app.Application
import android.os.Bundle

interface DefaultActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
        //do nothing
    }

    override fun onActivityStarted(activity: Activity) {
        //do nothing
    }

    override fun onActivityResumed(activity: Activity) {
        //do nothing
    }

    override fun onActivityPaused(activity: Activity) {
        //do nothing
    }

    override fun onActivityStopped(activity: Activity) {
        //do nothing
    }

    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {
        //do nothing
    }

    override fun onActivityDestroyed(activity: Activity) {
        //do nothing
    }
}