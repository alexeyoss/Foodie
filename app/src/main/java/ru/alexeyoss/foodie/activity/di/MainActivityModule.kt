package ru.alexeyoss.foodie.activity.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.alexeyoss.core.common.di.scope.PerActivity
import ru.alexeyoss.foodie.activity.MainActivityViewModel
import ru.alexeyoss.foodie.activity.domain.repositories.LocationRepository
import ru.alexeyoss.foodie.activity.repositories.AdapterLocationRepository

@Module
interface MainActivityModule {

    @Binds
    fun bindLocationRepository(impl: AdapterLocationRepository): LocationRepository

    @Binds
    @PerActivity
    @[IntoMap ClassKey(MainActivityViewModel::class)]
    fun bindMainActivityViewModel(mainActivityViewModel: MainActivityViewModel): ViewModel
}