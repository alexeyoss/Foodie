package ru.alexeyoss.foodie.di

import dagger.Component
import ru.alexeyoss.core.common.di.MainToolsComponent
import ru.alexeyoss.core.common.di.MainToolsProvider
import ru.alexeyoss.core.common.di.scope.PerApplication
import ru.alexeyoss.data.di.DataComponent
import ru.alexeyoss.data.di.DataProvider
import ru.alexeyoss.di.LocationModule
import ru.alexeyoss.features.cart.di.CartDeps
import ru.alexeyoss.features.categories.di.CategoriesDeps
import ru.alexeyoss.features.dishes.di.DishesDeps
import ru.alexeyoss.foodie.FoodieApp
import ru.alexeyoss.foodie.activity.MainActivity
import ru.alexeyoss.foodie.activity.di.MainActivityModule
import ru.alexeyoss.foodie.mediators.cart.di.CartMediatorModule
import ru.alexeyoss.foodie.mediators.categories.di.CategoriesMediatorModule
import ru.alexeyoss.foodie.mediators.dishes.di.DishesMediatorModule
import ru.alexeyoss.services.navigation.di.NavigationModule


interface AppComponentProvider : MainToolsProvider,
    DataProvider,
    CategoriesDeps,
    DishesDeps,
    CartDeps


@[PerApplication Component(
    modules = [
        MainActivityModule::class,

        CategoriesMediatorModule::class,
        DishesMediatorModule::class,
        CartMediatorModule::class,

        LocationModule::class,
        NavigationModule::class
    ],
    dependencies = [
        MainToolsProvider::class,
        DataProvider::class
    ]
)]

interface AppComponent : AppComponentProvider {
    fun inject(app: FoodieApp)

    fun inject(mainActivity: MainActivity)

    class Initializer private constructor() {
        companion object {
            fun init(app: FoodieApp): AppComponent {

                val mainToolsProvider = MainToolsComponent.Initializer.init(app)

                val dataProvider = DataComponent.Initializer.init()

                return DaggerAppComponent.builder()
                    .mainToolsProvider(mainToolsProvider)
                    .dataProvider(dataProvider)
                    .build()

            }
        }
    }
}



