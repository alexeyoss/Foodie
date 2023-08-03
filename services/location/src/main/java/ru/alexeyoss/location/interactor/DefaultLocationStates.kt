package ru.alexeyoss.location.interactor

import android.location.Location
import ru.alexeyoss.location.LocationService
import ru.alexeyoss.location.interactor.DefaultLocationStates.Error
import ru.alexeyoss.location.interactor.DefaultLocationStates.Loading
import ru.alexeyoss.location.interactor.DefaultLocationStates.Success

/**
 * Default location state
 * @see Loading
 * @see Error
 * @see Success
 * */
sealed interface DefaultLocationStates {
    object Loading : DefaultLocationStates
    data class Error(val exception: Exception) : DefaultLocationStates

    /**
     * Valid status with geo point from [LocationService]
     * */
    data class Success(val location: Location) : DefaultLocationStates

}