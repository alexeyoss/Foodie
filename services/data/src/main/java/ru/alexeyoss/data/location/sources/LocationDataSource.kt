package ru.alexeyoss.data.location.sources

import ru.alexeyoss.network.models.requests.LocationRequest
import ru.alexeyoss.network.models.responses.LocationDTO
import ru.alexeyoss.network.utils.ResponseStates

interface LocationDataSource {
    suspend fun getCityNameByCoords(locationRequest: LocationRequest): ResponseStates<LocationDTO>
}