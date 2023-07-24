package ru.alexeyoss.data.dishes.di

import dagger.Binds
import dagger.Module
import ru.alexeyoss.core.common.di.scope.PerApplication
import ru.alexeyoss.data.dishes.DishesDataRepository
import ru.alexeyoss.data.dishes.RealDishesDataRepository
import ru.alexeyoss.data.dishes.sources.DishesDataSource
import ru.alexeyoss.data.dishes.sources.DishesDataSourceImpl

@Module
internal interface DishesDataModule {


    @Binds
    @PerApplication
    fun bindDishesDataSource(impl: DishesDataSourceImpl): DishesDataSource

    @Binds
    @PerApplication
    fun bindDishesDataRepository(impl: RealDishesDataRepository): DishesDataRepository
}