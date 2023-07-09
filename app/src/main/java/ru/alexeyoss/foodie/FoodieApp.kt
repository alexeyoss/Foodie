package ru.alexeyoss.foodie

import android.app.Application
import android.content.Context
import ru.alexeyoss.features.categories.di.CategoriesDeps
import ru.alexeyoss.features.categories.di.provider.CategoriesComponentDepsProvider
import ru.alexeyoss.features.dishes.di.DishesDeps
import ru.alexeyoss.features.dishes.di.provider.DishesComponentDepsProvider
import ru.alexeyoss.foodie.di.AppComponent
import timber.log.Timber

class FoodieApp : Application(),
    CategoriesComponentDepsProvider,
    DishesComponentDepsProvider {

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
    override fun getDishesDeps(): DishesDeps = appComponent
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is FoodieApp -> appComponent
        else -> applicationContext.appComponent
    }
