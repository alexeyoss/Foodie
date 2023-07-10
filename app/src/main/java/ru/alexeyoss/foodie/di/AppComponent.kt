package ru.alexeyoss.foodie.di

import dagger.Component
import ru.alexeyoss.core.common.di.scope.PerApplication
import ru.alexeyoss.data.di.DataComponent
import ru.alexeyoss.data.di.DataProvider
import ru.alexeyoss.features.cart.di.CartDeps
import ru.alexeyoss.features.categories.di.CategoriesDeps
import ru.alexeyoss.features.dishes.di.DishesDeps
import ru.alexeyoss.foodie.FoodieApp
import ru.alexeyoss.foodie.mediators.cart.di.CartMediatorModule
import ru.alexeyoss.foodie.mediators.categories.di.CategoriesMediatorModule
import ru.alexeyoss.foodie.mediators.dishes.di.DishesMediatorModule
import ru.alexeyoss.foodie.navigation.MainActivity
import ru.alexeyoss.services.navigation.di.NavigationComponent
import ru.alexeyoss.services.navigation.di.NavigationProvider

interface AppComponentProvider :
    DataProvider,
    NavigationProvider,
    CategoriesDeps,
    DishesDeps,
    CartDeps


@[PerApplication Component(
    modules = [
        // HelpMe is it correct to have some mediators in inject section? Is it a bottleneck?
        CategoriesMediatorModule::class,
        DishesMediatorModule::class,
        CartMediatorModule::class,
    ],
    dependencies = [
        DataProvider::class,
        NavigationProvider::class
    ]
)]

interface AppComponent : AppComponentProvider {

    fun inject(app: FoodieApp)
    fun inject(mainActivity: MainActivity)

    class Initializer private constructor() {
        companion object {

            fun init(): AppComponent {
                val dataProvider = DataComponent.Initializer
                    .init()

                val navigationProvider = NavigationComponent.Initializer
                    .init()

                return DaggerAppComponent.builder()
                    .dataProvider(dataProvider)
                    .navigationProvider(navigationProvider)
                    .build()

            }
        }
    }

}




