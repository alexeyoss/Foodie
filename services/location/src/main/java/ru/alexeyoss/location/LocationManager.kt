package ru.alexeyoss.location

import android.content.Context
import android.location.Location
import androidx.annotation.RequiresPermission
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class LocationManager
@Inject
constructor(
    context: Context,
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
) {


    @RequiresPermission(
        anyOf = [
            "android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.ACCESS_FINE_LOCATION"
        ]
    )
    fun getLastKnownLocation(): Flow<Location> =
        callbackFlow {
            fusedLocationClient
                .lastLocation
                .addOnSuccessListener { location ->
                    if (location == null) {
                        close(NullPointerException())
                    } else {
                        trySend(location)
                    }
                }
                .addOnFailureListener { exception -> close(exception) }
        }

    @RequiresPermission(
        anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"]
    )
    fun observeLocationUpdates(
        intervalMillis: Long?,
        fastestIntervalMillis: Long?,
        priority: LocationPriority?
    ): Observable<Location> {
        var locationCallback: LocationCallback? = null
        return Observable
            .create<Location> { observableEmitter ->
                val locationRequest =
                    LocationUtil.createLocationRequest(intervalMillis, fastestIntervalMillis, priority)
                locationCallback = createLocationCallback(observableEmitter)
                fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
            }
            .subscribeOn(AndroidSchedulers.mainThread())
            .doFinally { fusedLocationClient.removeLocationUpdates(locationCallback) }
    }


    private fun createLocationCallback(observableEmitter: MutableLiveData<Location>): LocationCallback =
        object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                observableEmitter.value = p0.lastLocation
            }
        }
}