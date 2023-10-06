package ru.alexeyoss.foodie.services.data.dishes.di

import dagger.Binds
import dagger.Module
import ru.alexeyoss.foodie.core.common.di.scope.PerApplication
import ru.alexeyoss.foodie.services.data.dishes.DishesDataRepository
import ru.alexeyoss.foodie.services.data.dishes.RealDishesDataRepository
import ru.alexeyoss.foodie.services.data.dishes.sources.DishesDataSource
import ru.alexeyoss.foodie.services.data.dishes.sources.DishesDataSourceImpl

@Module
internal interface DishesDataModule {

    @Binds
    @PerApplication
    fun bindDishesDataSource(impl: DishesDataSourceImpl): DishesDataSource

    @Binds
    @PerApplication
    fun bindDishesDataRepository(impl: RealDishesDataRepository): DishesDataRepository
}
