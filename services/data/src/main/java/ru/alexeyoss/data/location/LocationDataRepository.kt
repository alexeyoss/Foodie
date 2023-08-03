package ru.alexeyoss.data.location

import ru.alexeyoss.network.models.requests.LocationRequest
import ru.alexeyoss.network.models.responses.LocationDTO
import ru.alexeyoss.network.utils.ResponseStates

interface LocationDataRepository {
    suspend fun getCityNameByCoords(locationRequest: LocationRequest): ResponseStates<LocationDTO>
}