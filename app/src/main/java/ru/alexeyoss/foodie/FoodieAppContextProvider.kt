package ru.alexeyoss.foodie

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentActivity
import ru.alexeyoss.foodie.core.common.activity.ActiveActivityHolder
import ru.alexeyoss.foodie.core.common.activity.DefaultActivityLifecycleCallbacks
import ru.alexeyoss.foodie.core.common.di.AppContextProvider
import ru.alexeyoss.foodie.di.AppComponent
import ru.alexeyoss.foodie.di.AppComponentDepsProvider
import timber.log.Timber
import javax.inject.Inject

class FoodieAppContextProvider :
    Application(),
    AppContextProvider,
    AppComponentDepsProvider {

    override val appComponent: AppComponent by lazy {
        AppComponent.Initializer.init(this@FoodieAppContextProvider)
    }

    @Inject
    lateinit var activeActivityHolder: ActiveActivityHolder

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this@FoodieAppContextProvider)
        registerActiveActivityListener()
        setDebugLogging()
    }

    private fun registerActiveActivityListener() {
        registerActivityLifecycleCallbacks(object : DefaultActivityLifecycleCallbacks {

            override fun onActivityResumed(activity: Activity) {
                activeActivityHolder.registerActiveActivity(activity as FragmentActivity)
            }

            override fun onActivityPaused(activity: Activity) {
                activeActivityHolder.removeActiveActivity()
            }
        })
    }

    private fun setDebugLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is FoodieAppContextProvider -> appComponent
        else -> applicationContext.appComponent
    }
