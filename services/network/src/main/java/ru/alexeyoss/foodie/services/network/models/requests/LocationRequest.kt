package ru.alexeyoss.foodie.services.network.models.requests

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LocationRequest(
    @SerializedName("longitude")
    @Expose
    val longitude: Double,
    @SerializedName("latitude")
    @Expose
    val latitude: Double,
)