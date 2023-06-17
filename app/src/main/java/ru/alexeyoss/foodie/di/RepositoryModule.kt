package ru.alexeyoss.foodie.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.alexeyoss.features.categories.domain.CategoryRepository

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun bindMainRepository(impl: CategoryRepositoryImpl): ru.alexeyoss.features.categories.domain.CategoryRepository
}