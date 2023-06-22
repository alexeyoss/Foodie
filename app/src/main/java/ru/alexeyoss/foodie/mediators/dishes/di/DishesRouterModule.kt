package ru.alexeyoss.foodie.mediators.dishes.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import ru.alexeyoss.features.dishes.presentation.DishesRouter
import ru.alexeyoss.foodie.mediators.dishes.AdapterDishesRouter

@Module
@InstallIn(ActivityRetainedComponent::class)
interface DishesRouterModule {

    @Binds
    fun bindDishesRouter(impl: AdapterDishesRouter): DishesRouter
}