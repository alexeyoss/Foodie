package ru.alexeyoss.foodie

import android.app.Application
import ru.alexeyoss.features.categories.di.CategoriesDeps
import ru.alexeyoss.features.categories.di.provider.CategoriesComponentDepsProvider
import ru.alexeyoss.features.mainscreen.di.MainActivityDeps
import ru.alexeyoss.features.mainscreen.di.MainActivityDepsProvider
import ru.alexeyoss.foodie.di.AppComponent
import timber.log.Timber

class FoodieApp : Application(),
    CategoriesComponentDepsProvider,
    MainActivityDepsProvider {

    val appComponent: AppComponent by lazy {
        AppComponent.Initializer.init()
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this@FoodieApp)
        setDebugLogging()
    }

    private fun setDebugLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    // HelpMe The best way how provide deps without boilerplate code
    override fun getCategoryDeps(): CategoriesDeps = appComponent
    override fun getMainActivityDeps(): MainActivityDeps = appComponent
}
