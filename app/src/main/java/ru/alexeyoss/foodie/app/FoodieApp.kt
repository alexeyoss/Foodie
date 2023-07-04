package ru.alexeyoss.foodie.app

import android.app.Application
import android.content.Context
import ru.alexeyoss.features.categories.di.CategoriesDeps
import ru.alexeyoss.features.categories.di.provider.CategoriesComponentDepsProvider
import ru.alexeyoss.foodie.BuildConfig
import timber.log.Timber

class FoodieApp : Application(), CategoriesComponentDepsProvider {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().application(this).build()
    }

    override fun onCreate() {
        super.onCreate()
        setDebugLogging()
    }

    private fun setDebugLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun getCategoriesDeps(): CategoriesDeps = appComponent
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is FoodieApp -> appComponent
        else -> applicationContext.appComponent
    }
