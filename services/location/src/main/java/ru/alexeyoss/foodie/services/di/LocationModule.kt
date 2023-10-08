package ru.alexeyoss.foodie.services.di

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import ru.alexeyoss.foodie.core.common.di.AppContextProvider
import ru.alexeyoss.foodie.services.location.LocationService
import ru.alexeyoss.foodie.services.location.interactor.DefaultLocationInteractor

@Module
object LocationModule {
    @Provides
    fun provideFusedLocationProviderClient(appContextProvider: AppContextProvider): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(appContextProvider.getApplicationContext())
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
