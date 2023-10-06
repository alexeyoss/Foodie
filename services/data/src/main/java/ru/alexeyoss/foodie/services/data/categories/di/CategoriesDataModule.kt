package ru.alexeyoss.foodie.services.data.categories.di

import dagger.Binds
import dagger.Module
import ru.alexeyoss.foodie.core.common.di.scope.PerApplication
import ru.alexeyoss.foodie.services.data.categories.CategoriesDataRepository
import ru.alexeyoss.foodie.services.data.categories.RealCategoriesDataRepository
import ru.alexeyoss.foodie.services.data.categories.sources.CategoriesDataSource
import ru.alexeyoss.foodie.services.data.categories.sources.CategoriesDataSourceImpl

@Module
internal interface CategoriesDataModule {

    @Binds
    @PerApplication
    fun bindCategoriesDataRepository(impl: RealCategoriesDataRepository): CategoriesDataRepository

    @Binds
    @PerApplication
    fun bindCategoriesDataSource(impl: CategoriesDataSourceImpl): CategoriesDataSource
}
