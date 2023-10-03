package ru.alexeyoss.foodie.services.data.location.di

import dagger.Binds
import dagger.Module
import ru.alexeyoss.core.common.di.scope.PerApplication
import ru.alexeyoss.foodie.services.data.location.LocationDataRepository
import ru.alexeyoss.foodie.services.data.location.RealLocationDataRepository
import ru.alexeyoss.foodie.services.data.location.sources.LocationDataSource
import ru.alexeyoss.foodie.services.data.location.sources.LocationDataSourceImpl

@Module
interface LocationDataModule {

    @Binds
    @PerApplication
    fun bindLocationDataRepository(impl: RealLocationDataRepository): LocationDataRepository

    @Binds
    @PerApplication
    fun bindLocationDataSource(impl: LocationDataSourceImpl): LocationDataSource

}