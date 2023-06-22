package ru.alexeyoss.data.dishes.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.alexeyoss.data.dishes.sources.DishesDataSource
import ru.alexeyoss.data.dishes.sources.DishesDataSourceImpl


@Module
@InstallIn(SingletonComponent::class)
interface DishesDataSourcesModule {

    @Binds
    fun bindDishesDataSource(impl: DishesDataSourceImpl): DishesDataSource

}