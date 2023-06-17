package ru.alexeyoss.foodie.mediators.categories.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.alexeyoss.features.categories.domain.repositories.CategoriesRepository
import ru.alexeyoss.foodie.mediators.categories.repositories.AdapterCategoriesRepository

@Module
@InstallIn(SingletonComponent::class)
interface CategoriesRepositoriesModule {

    @Binds
    fun bindCategoriesRepository(impl: AdapterCategoriesRepository): CategoriesRepository
}