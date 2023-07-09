package ru.alexeyoss.foodie.mediators.dishes.di

import dagger.Binds
import dagger.Module
import ru.alexeyoss.features.dishes.domain.repositories.DishesRepository
import ru.alexeyoss.features.dishes.presentation.DishesRouter
import ru.alexeyoss.foodie.mediators.dishes.AdapterDishesRouter
import ru.alexeyoss.foodie.mediators.dishes.repositories.AdapterDishesRepository

@Module
internal interface DishesMediatorModule {

    @Binds
    fun bindDishesRepository(impl: AdapterDishesRepository): DishesRepository

    @Binds
    fun bindDishesRouter(impl: AdapterDishesRouter): DishesRouter
}