package ru.alexeyoss.data.dishes.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.alexeyoss.data.DishesDataRepository
import ru.alexeyoss.data.dishes.RealDishesDataRepository

@Module
@InstallIn(SingletonComponent::class)
interface DishesDataRepositoriesModule {

    @Binds
    fun bindDishesDataRepository(impl: RealDishesDataRepository): DishesDataRepository
}