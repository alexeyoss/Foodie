package ru.alexeyoss.location.interactor

import android.location.Location
import ru.alexeyoss.location.LocationService

/**
 * Default location state
 * @see DefaultLocationInteractor
 * @see LocationService
 * */
sealed interface DefaultLocationStates {
    object Loading : DefaultLocationStates

    /**
     * Standard success location state based on headquarter geo point
     * @see MOSCOW
     * */
    data class SuccessWithDefaultLocation(
        val location: Location = Location("DEFAULT_LOCATION_PROVIDER_NAME").apply {
            latitude = MOSCOW.first
            longitude = MOSCOW.second
        }
    ) : DefaultLocationStates

    /**
     * Valid status with geo point from [LocationService]
     * */
    data class SuccessWithCurrentLocation(val location: Location) : DefaultLocationStates

    companion object {
        @JvmStatic
        val MOSCOW = 37.54 to 55.74
    }
}