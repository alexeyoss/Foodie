package ru.alexeyoss.foodie.mediators.categories.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.alexeyoss.foodie.mediators.categories.mappers.CategoryMapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CategoriesMappersModule {

    @Singleton
    @Provides
    fun providedCategoryMapper(): CategoryMapper = CategoryMapper()
}