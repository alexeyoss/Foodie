package ru.alexeyoss.data.categories.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.alexeyoss.data.CategoriesDataRepository
import ru.alexeyoss.data.categories.RealCategoriesDataRepository

@Module
@InstallIn(SingletonComponent::class)
interface CategoriesDataRepositoriesModule {

    @Binds
    fun bindCategoriesDataRepository(impl: RealCategoriesDataRepository): CategoriesDataRepository
}