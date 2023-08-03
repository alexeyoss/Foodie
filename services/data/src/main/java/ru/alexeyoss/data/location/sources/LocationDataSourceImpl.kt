package ru.alexeyoss.data.location.sources

import ru.alexeyoss.network.MainApiService
import ru.alexeyoss.network.models.requests.LocationRequest
import ru.alexeyoss.network.models.responses.LocationDTO
import ru.alexeyoss.network.utils.ResponseStates
import ru.alexeyoss.network.utils.safeApiCall
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