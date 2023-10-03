package ru.alexeyoss.foodie.services.data.location

import ru.alexeyoss.foodie.services.network.models.requests.LocationRequest
import ru.alexeyoss.foodie.services.network.models.responses.LocationDTO
import ru.alexeyoss.foodie.services.network.utils.ResponseStates

interface LocationDataRepository {
    suspend fun getCityNameByCoords(locationRequest: LocationRequest): ResponseStates<LocationDTO>
}