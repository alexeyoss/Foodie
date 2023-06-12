package ru.alexeyoss.foodie.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.alexeyoss.foodie.data.MainRepositoryImpl
import ru.alexeyoss.foodie.domain.MainRepository

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun bindMainRepository(impl: MainRepositoryImpl): MainRepository
}