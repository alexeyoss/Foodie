package ru.alexeyoss.foodie.activity.domain

import androidx.annotation.RequiresPermission
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.alexeyoss.foodie.core.common.data.Container
import ru.alexeyoss.foodie.activity.domain.entities.UiLocationInfo
import ru.alexeyoss.foodie.activity.domain.repositories.LocationRepository
import ru.alexeyoss.foodie.services.location.interactor.DefaultLocationInteractor
import ru.alexeyoss.foodie.services.location.interactor.DefaultLocationStates
import javax.inject.Inject

class GetCityNameByCoords
@Inject
constructor(
    private val locationRepository: LocationRepository,
    private val defaultLocationInteractor: DefaultLocationInteractor
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    @RequiresPermission(
        anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"]
    )
    suspend operator fun invoke(): Flow<Container<UiLocationInfo>> {
        return defaultLocationInteractor.getLastKnownLocation()
            .map { locationState ->
                when (locationState) {
                    is DefaultLocationStates.Success -> locationState.location
                    else -> null
                }
            }
            .flatMapLatest { location ->
                if (location != null) {
                    locationRepository.getCityNameByCoords(location)
                } else locationRepository.getDefaultCityName()
            }
    }


}