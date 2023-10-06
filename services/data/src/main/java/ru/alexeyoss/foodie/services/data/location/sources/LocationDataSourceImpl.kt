package ru.alexeyoss.foodie.services.data.location.sources

import ru.alexeyoss.foodie.services.network.MainApiService
import ru.alexeyoss.foodie.services.network.models.requests.LocationRequest
import ru.alexeyoss.foodie.services.network.models.responses.LocationDTO
import ru.alexeyoss.foodie.services.network.utils.ResponseStates
import ru.alexeyoss.foodie.services.network.utils.safeApiCall
import javax.inject.Inject

class LocationDataSourceImpl
@Inject constructor(
    private val apiService: MainApiService
) : LocationDataSource {
    override suspend fun getCityNameByCoords(locationRequest: LocationRequest): ResponseStates<LocationDTO> {
        return safeApiCall {
            apiService.getCityNameByCoords(locationRequest)
        }
    }
}