package ru.alexeyoss.foodie.services.location

import android.location.Location
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.FusedLocationProviderClient
import ru.alexeyoss.core.common.data.Container
import ru.alexeyoss.foodie.services.location.exceptions.LocationNullPointerException
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Service based on [FusedLocationProviderClient]
 * */
class LocationService
@Inject
constructor(
    private val fusedLocationClient: FusedLocationProviderClient
) {

    /**
     * Запросить последнее известное местоположение.
     *
     * @return [Container]:
     * - Success() вызывается в случае удачного получения местоположения;
     * - Error() вызывается в случае, если местоположение было получено, но равно null;
     * - resumeWithException() вызывается, если нет возможности получить местоположение.
     *  Вызывается [CompositeException],содержащее список возможных исключений:
     * [NoLocationPermissionException], [PlayServicesAreNotAvailableException],
     * [ResolvableApiException].
     */
    @RequiresPermission(
        anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"]
    )
    suspend fun getLastKnownLocation(): Container<Location> {
        return suspendCoroutine { continuation ->
            fusedLocationClient
                .lastLocation
                .addOnSuccessListener { location ->
                    if (location == null) {
                        continuation.resume(Container.Error(LocationNullPointerException()))
                    } else {
                        continuation.resume(Container.Success(location))
                    }
                }.addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
    }
}