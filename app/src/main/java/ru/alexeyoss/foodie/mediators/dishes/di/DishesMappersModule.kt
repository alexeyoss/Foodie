package ru.alexeyoss.foodie.mediators.dishes.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.alexeyoss.foodie.mediators.dishes.mappers.DishesMapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DishesMappersModule {

    @Singleton
    @Provides
    fun providedDishesMapper(): DishesMapper = DishesMapper()
}