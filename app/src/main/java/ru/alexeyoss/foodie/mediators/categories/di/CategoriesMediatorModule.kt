package ru.alexeyoss.foodie.mediators.categories.di

import dagger.Binds
import dagger.Module
import ru.alexeyoss.foodie.features.categories.domain.repositories.CategoriesRepository
import ru.alexeyoss.foodie.features.categories.presentation.CategoryRouter
import ru.alexeyoss.foodie.mediators.categories.AdapterCategoriesRouter
import ru.alexeyoss.foodie.mediators.categories.repositories.AdapterCategoriesRepository


@Module
internal interface CategoriesMediatorModule {
    @Binds
    fun bindCategoriesRepository(impl: AdapterCategoriesRepository): CategoriesRepository

    @Binds
    fun bindCategoriesRouter(impl: AdapterCategoriesRouter): CategoryRouter

}