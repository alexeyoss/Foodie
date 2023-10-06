package ru.alexeyoss.foodie.services.data.location

import ru.alexeyoss.foodie.services.data.location.sources.LocationDataSource
import ru.alexeyoss.foodie.services.network.models.requests.LocationRequest
import ru.alexeyoss.foodie.services.network.models.responses.LocationDTO
import ru.alexeyoss.foodie.services.network.utils.ResponseStates
import javax.inject.Inject

class RealLocationDataRepository
@Inject constructor(
    private val locationDataSource: LocationDataSource
) : LocationDataRepository {

    override suspend fun getCityNameByCoords(locationRequest: LocationRequest): ResponseStates<LocationDTO> {
        return locationDataSource.getCityNameByCoords(locationRequest)
    }
}
