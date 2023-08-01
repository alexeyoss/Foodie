package ru.alexeyoss.di

import android.app.Application
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.alexeyoss.core.common.di.scope.PerActivity
import ru.alexeyoss.location.interactor.DefaultLocationInteractor
import ru.alexeyoss.location.LocationService


interface LocationProvider {
    fun getLocationService(): LocationService
    fun getDefaultLocationInteractor(): DefaultLocationInteractor
}

@[PerActivity Component(
    modules = [LocationModule::class]
)]
interface LocationComponent : LocationProvider {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun deps(context: Application): Builder
        fun build(): LocationComponent
    }
}

@Module
internal object LocationModule {
    @Provides
    fun provideFusedLocationProviderClient(context: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
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