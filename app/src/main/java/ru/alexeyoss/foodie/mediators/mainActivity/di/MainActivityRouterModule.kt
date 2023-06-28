package ru.alexeyoss.foodie.mediators.mainActivity.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import ru.alexeyoss.foodie.MainActivityRouter
import ru.alexeyoss.foodie.mediators.mainActivity.AdapterMainActivityRouter

@Module
@InstallIn(ActivityRetainedComponent::class)
interface MainActivityRouterModule {

    @Binds
    fun bindMainActivityRouter(impl: AdapterMainActivityRouter): MainActivityRouter
}