package ru.alexeyoss.foodie.activity.repositories

import android.location.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.alexeyoss.foodie.core.common.data.Container
import ru.alexeyoss.foodie.services.data.location.LocationDataRepository
import ru.alexeyoss.foodie.activity.domain.entities.UiLocationInfo
import ru.alexeyoss.foodie.activity.domain.repositories.LocationRepository
import ru.alexeyoss.foodie.activity.mappers.LocationMapper
import ru.alexeyoss.foodie.mediators.buildNetworkFlow
import ru.alexeyoss.foodie.services.location.ConstLocations
import ru.alexeyoss.foodie.services.network.models.requests.LocationRequest
import javax.inject.Inject

class AdapterLocationRepository
@Inject
constructor(
    private val locationDataRepository: LocationDataRepository,
    private val locationMapper: LocationMapper
) : LocationRepository {

    override suspend fun getCityNameByCoords(location: Location): Flow<Container<UiLocationInfo>> {
        return buildNetworkFlow {
            /** Build [LocationRequest] model */
            val locationRequest = locationMapper.mapToLocationRequest(location)

            locationDataRepository.getCityNameByCoords(locationRequest)
        }.map { container ->
            container.suspendConvert { locationDTO ->
                locationMapper.mapToDomainModel(locationDTO)
            }
        }
    }

    override suspend fun getDefaultCityName(): Flow<Container<UiLocationInfo>> {
        return flow {
            emit(Container.Loading)
            val defaultUiLocationInfo = UiLocationInfo(
                cityName = ConstLocations.DEFAULT_CITY.cityName
            )
            emit(Container.Success(defaultUiLocationInfo))
        }
    }
}