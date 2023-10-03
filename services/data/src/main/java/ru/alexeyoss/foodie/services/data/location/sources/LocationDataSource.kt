package ru.alexeyoss.foodie.services.data.location.sources

import ru.alexeyoss.foodie.services.network.models.requests.LocationRequest
import ru.alexeyoss.foodie.services.network.models.responses.LocationDTO
import ru.alexeyoss.foodie.services.network.utils.ResponseStates

interface LocationDataSource {
    suspend fun getCityNameByCoords(locationRequest: LocationRequest): ResponseStates<LocationDTO>
}