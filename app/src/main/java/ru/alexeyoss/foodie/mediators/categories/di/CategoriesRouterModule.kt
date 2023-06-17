package ru.alexeyoss.foodie.mediators.categories.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import ru.alexeyoss.features.categories.presentation.CategoryRouter
import ru.alexeyoss.foodie.mediators.categories.AdapterCategoriesRouter

@Module
@InstallIn(ActivityRetainedComponent::class)
interface CategoriesRouterModule {

    @Binds
    fun bindCategoriesRouter(impl: AdapterCategoriesRouter): CategoryRouter
}