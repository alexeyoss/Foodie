package ru.alexeyoss.data.location

import ru.alexeyoss.data.location.sources.LocationDataSource
import ru.alexeyoss.network.models.requests.LocationRequest
import ru.alexeyoss.network.models.responses.LocationDTO
import ru.alexeyoss.network.utils.ResponseStates
import javax.inject.Inject

class RealLocationDataRepository
@Inject constructor(
    private val locationDataSource: LocationDataSource
) : LocationDataRepository {
    override suspend fun getCityNameByCoords(locationRequest: LocationRequest): ResponseStates<LocationDTO> {
        return locationDataSource.getCityNameByCoords(locationRequest)
    }
}