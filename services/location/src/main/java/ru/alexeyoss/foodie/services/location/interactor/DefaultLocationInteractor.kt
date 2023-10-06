package ru.alexeyoss.foodie.services.location.interactor

import androidx.annotation.RequiresPermission
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.alexeyoss.foodie.core.common.data.Container
import ru.alexeyoss.foodie.services.location.LocationService
import ru.alexeyoss.foodie.services.location.exceptions.LocationNullPointerException
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
    @RequiresPermission(
        anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"]
    )
    suspend fun getLastKnownLocation(): Flow<DefaultLocationStates> = flow {
        val locationState = when (val container = locationService.getLastKnownLocation()) {
            is Container.Loading -> DefaultLocationStates.Loading
            is Container.Error -> DefaultLocationStates.Error(container.exception)
            is Container.Success -> DefaultLocationStates.Success(container.value)
        }
        emit(locationState)
    }
}
