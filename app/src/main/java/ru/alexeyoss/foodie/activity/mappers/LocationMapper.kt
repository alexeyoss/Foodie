package ru.alexeyoss.foodie.activity.mappers

import android.location.Location
import ru.alexeyoss.foodie.core.common.data.BaseMapper
import ru.alexeyoss.foodie.activity.domain.entities.UiLocationInfo
import ru.alexeyoss.foodie.services.network.models.requests.LocationRequest
import ru.alexeyoss.foodie.services.network.models.responses.LocationDTO
import javax.inject.Inject

class LocationMapper
@Inject constructor() : BaseMapper<UiLocationInfo, LocationDTO> {
    override fun mapToDomainModel(foreignModel: LocationDTO): UiLocationInfo {
        return UiLocationInfo(
            cityName = foreignModel.cityName
        )
    }

    override fun mapToForeignModel(domainModel: UiLocationInfo): LocationDTO {
        return LocationDTO(
            cityName = domainModel.cityName
        )
    }

    fun mapToLocationRequest(location: Location): LocationRequest {
        return LocationRequest(
            longitude = location.longitude,
            latitude = location.latitude
        )
    }
}