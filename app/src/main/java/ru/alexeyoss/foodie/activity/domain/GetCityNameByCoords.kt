package ru.alexeyoss.foodie.activity.domain

import android.location.Location
import androidx.annotation.RequiresPermission
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import ru.alexeyoss.core.common.data.Container
import ru.alexeyoss.foodie.activity.domain.entities.UiLocationInfo
import ru.alexeyoss.foodie.activity.domain.repositories.LocationRepository
import ru.alexeyoss.location.interactor.DefaultLocationInteractor
import ru.alexeyoss.location.interactor.DefaultLocationStates
import javax.inject.Inject

class GetCityNameByCoords
@Inject
constructor(
    private val locationRepository: LocationRepository,
    private val defaultLocationInteractor: DefaultLocationInteractor
) {
    @RequiresPermission(
        anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"]
    )
    suspend operator fun invoke(): Flow<Container<UiLocationInfo>> {
        return defaultLocationInteractor.getLastKnownLocation()
            .map { locationState ->
                when (locationState) {
                    is DefaultLocationStates.Success -> locationState.location
                    else -> Location("DEFAULT_LOCATION_PROVIDER_NAME").apply {
                        latitude = MOSCOW_LATITUDE
                        longitude = MOSCOW_LONGITUDE
                    }
                }
            }
            .flowOn(Dispatchers.Main)
            .mapLatest { location ->
                locationRepository.getCityNameByCoords(location)
            }
            .last()
            .flowOn(Dispatchers.IO)

    }

    companion object {
        const val MOSCOW_LATITUDE = 37.54
        const val MOSCOW_LONGITUDE = 55.74
    }
}