package ru.alexeyoss.foodie.di

import dagger.Component
import ru.alexeyoss.core.common.di.scope.PerApplication
import ru.alexeyoss.data.di.DataComponent
import ru.alexeyoss.data.di.DataProvider
import ru.alexeyoss.features.categories.di.CategoriesDeps
import ru.alexeyoss.features.mainscreen.di.MainActivityDeps
import ru.alexeyoss.foodie.FoodieApp
import ru.alexeyoss.foodie.mediators.categories.di.CategoriesMediatorModule
import ru.alexeyoss.foodie.mediators.dishes.di.DishesMediatorModule

interface AppComponentProvider :
    DataProvider,
    CategoriesDeps,
    MainActivityDeps

@[PerApplication Component(
    modules = [
        // HelpMe is it correct to have some mediators in inject section? Is it a bottleneck?
        CategoriesMediatorModule::class,
        DishesMediatorModule::class
    ],
    dependencies = [DataProvider::class]
)]
interface AppComponent : AppComponentProvider {
    fun inject(app: FoodieApp)

    class Initializer private constructor() {
        companion object {

            fun init(): AppComponent {
                val dataProvider = DataComponent.Initializer
                    .init()

                return DaggerAppComponent.builder()
                    .dataProvider(dataProvider)
                    .build()

            }
        }
    }

}




