package ru.alexeyoss.data.di

import dagger.Component
import ru.alexeyoss.core.common.di.scope.PerApplication
import ru.alexeyoss.data.categories.CategoriesDataRepository
import ru.alexeyoss.data.categories.di.CategoriesDataModule
import ru.alexeyoss.data.dishes.DishesDataRepository
import ru.alexeyoss.data.dishes.di.DishesDataModule
import ru.alexeyoss.network.di.DaggerNetworkComponent
import ru.alexeyoss.network.di.NetworkProvider

interface DataProvider {
    fun getCategoriesDataRepository(): CategoriesDataRepository
    fun getDishesDataRepository(): DishesDataRepository
}

@[PerApplication Component(
    modules = [CategoriesDataModule::class, DishesDataModule::class],
    dependencies = [NetworkProvider::class]
)]
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

