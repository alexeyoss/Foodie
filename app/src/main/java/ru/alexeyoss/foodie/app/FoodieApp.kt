package ru.alexeyoss.foodie.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import ru.alexeyoss.foodie.BuildConfig
import timber.log.Timber

@HiltAndroidApp
class FoodieApp : Application() {

    override fun onCreate() {
        super.onCreate()
        setDebugLogging()
    }

    private fun setDebugLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}