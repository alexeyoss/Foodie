package ru.alexeyoss.foodie.services.data.di

import dagger.Component
import ru.alexeyoss.core.common.di.scope.PerApplication
import ru.alexeyoss.foodie.services.data.categories.CategoriesDataRepository
import ru.alexeyoss.foodie.services.data.categories.di.CategoriesDataModule
import ru.alexeyoss.foodie.services.data.dishes.DishesDataRepository
import ru.alexeyoss.foodie.services.data.dishes.di.DishesDataModule
import ru.alexeyoss.foodie.services.data.location.LocationDataRepository
import ru.alexeyoss.foodie.services.data.location.di.LocationDataModule
import ru.alexeyoss.network.di.DaggerNetworkComponent
import ru.alexeyoss.foodie.services.network.di.NetworkProvider

interface DataProvider {
    fun getCategoriesDataRepository(): CategoriesDataRepository
    fun getDishesDataRepository(): DishesDataRepository
    fun getLocationDataRepository(): LocationDataRepository
}

@[
PerApplication Component(
    modules = [
        CategoriesDataModule::class,
        DishesDataModule::class,
        LocationDataModule::class
    ],
    dependencies = [NetworkProvider::class]
)
]
interface DataComponent : DataProvider {

    class Initializer private constructor() {
        companion object {

            fun init(): DataProvider {
                val networkComponent = DaggerNetworkComponent.builder().build()

                return DaggerDataComponent.builder()
                    .networkProvider(networkComponent)
                    .build()
            }
        }
    }
}
