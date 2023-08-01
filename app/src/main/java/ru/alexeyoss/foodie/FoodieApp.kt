package ru.alexeyoss.foodie

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentActivity
import ru.alexeyoss.core.common.activity.ActiveActivityHolder
import ru.alexeyoss.core.common.activity.DefaultActivityLifecycleCallbacks
import ru.alexeyoss.core.common.di.App
import ru.alexeyoss.features.cart.di.CartDeps
import ru.alexeyoss.features.cart.di.provider.CartComponentDepsProvider
import ru.alexeyoss.features.categories.di.CategoriesDeps
import ru.alexeyoss.features.categories.di.provider.CategoriesComponentDepsProvider
import ru.alexeyoss.features.dishes.di.DishesDeps
import ru.alexeyoss.features.dishes.di.provider.DishesComponentDepsProvider
import ru.alexeyoss.foodie.di.AppComponent
import timber.log.Timber
import javax.inject.Inject

class FoodieApp : Application(),
    App,
    CategoriesComponentDepsProvider,
    DishesComponentDepsProvider,
    CartComponentDepsProvider {

    val appComponent: AppComponent by lazy {
        AppComponent.Initializer.init(this@FoodieApp)
    }

    @Inject
    lateinit var activeActivityHolder: ActiveActivityHolder

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this@FoodieApp)
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

    // TODO build common AppComponentProvider interface for providing AppComponent
    override fun getCategoryDeps(): CategoriesDeps = appComponent
    override fun getDishesDeps(): DishesDeps = appComponent
    override fun getCartDeps(): CartDeps = appComponent
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is FoodieApp -> appComponent
        else -> applicationContext.appComponent
    }
