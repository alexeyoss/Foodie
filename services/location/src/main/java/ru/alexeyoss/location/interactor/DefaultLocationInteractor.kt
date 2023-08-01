package ru.alexeyoss.location.interactor

import android.annotation.SuppressLint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.alexeyoss.core.common.data.Container
import ru.alexeyoss.location.LocationService
import ru.alexeyoss.location.exceptions.LocationNullPointerException
import javax.inject.Inject

/**
 * Default interactor for providing most common use cases
 * */
class DefaultLocationInteractor
@Inject constructor(
    private val locationService: LocationService
) {

    /**
     * Scenario for providing DEFAULT location when the [LocationNullPointerException] occur
     * @return [DefaultLocationStates]
     * */
    @SuppressLint("MissingPermission")
    suspend fun getLastKnownLocation(): Flow<DefaultLocationStates> {
        return flow {
            emit(DefaultLocationStates.Loading)
            val locationState = when (val container = locationService.getLastKnownLocation()) {
                is Container.Error -> DefaultLocationStates.SuccessWithDefaultLocation()
                is Container.Loading -> DefaultLocationStates.Loading
                is Container.Success -> DefaultLocationStates.SuccessWithCurrentLocation(container.value)
            }
            emit(locationState)
        }
    }

}