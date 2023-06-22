package ru.alexeyoss.foodie.mediators.dishes.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.alexeyoss.features.dishes.domain.repositories.DishesRepository
import ru.alexeyoss.foodie.mediators.dishes.repositories.AdapterDishesRepository


@Module
@InstallIn(SingletonComponent::class)
interface DishesRepositoriesModule {

    @Binds
    fun bindDishesRepository(impl: AdapterDishesRepository): DishesRepository
}