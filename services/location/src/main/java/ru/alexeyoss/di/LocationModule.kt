package ru.alexeyoss.di

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import ru.alexeyoss.core.common.di.App
import ru.alexeyoss.location.LocationService
import ru.alexeyoss.location.interactor.DefaultLocationInteractor

@Module
object LocationModule {
    @Provides
    fun provideFusedLocationProviderClient(app: App): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(app.getApplicationContext())
    }

    @Provides
    fun provideLocationService(fusedLocationClient: FusedLocationProviderClient): LocationService {
        return LocationService(fusedLocationClient)
    }

    @Provides
    fun provideDefaultLocationInteractor(locationService: LocationService): DefaultLocationInteractor {
        return DefaultLocationInteractor(locationService)
    }
}