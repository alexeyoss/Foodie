package ru.alexeyoss.data.categories.di

import dagger.Binds
import dagger.Module
import ru.alexeyoss.data.CategoriesDataRepository
import ru.alexeyoss.data.categories.RealCategoriesDataRepository
import ru.alexeyoss.data.categories.sources.CategoriesDataSource
import ru.alexeyoss.data.categories.sources.CategoriesDataSourceImpl

@Module
interface CategoriesDataModule {

    @Binds
    fun bindCategoriesDataRepository(impl: RealCategoriesDataRepository): CategoriesDataRepository


    @Binds
    fun bindCategoriesDataSource(impl: CategoriesDataSourceImpl): CategoriesDataSource
}