package ru.alexeyoss.data.categories.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.alexeyoss.data.CategoriesDataRepository
import ru.alexeyoss.data.categories.RealCategoriesDataRepository
import ru.alexeyoss.data.categories.sources.CategoriesDataSource
import ru.alexeyoss.data.categories.sources.CategoriesDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
interface CategoriesDataSourcesModule {

    @Binds
    fun bindCategoriesDataSource(impl: CategoriesDataSourceImpl): CategoriesDataSource
}