package ru.alexeyoss.data.categories.di

import dagger.Binds
import dagger.Module
import ru.alexeyoss.core.common.di.scope.PerApplication
import ru.alexeyoss.data.categories.CategoriesDataRepository
import ru.alexeyoss.data.categories.RealCategoriesDataRepository
import ru.alexeyoss.data.categories.sources.CategoriesDataSource
import ru.alexeyoss.data.categories.sources.CategoriesDataSourceImpl

@Module
internal interface CategoriesDataModule {

    @Binds
    @PerApplication
    fun bindCategoriesDataRepository(impl: RealCategoriesDataRepository): CategoriesDataRepository


    @Binds
    @PerApplication
    fun bindCategoriesDataSource(impl: CategoriesDataSourceImpl): CategoriesDataSource
}