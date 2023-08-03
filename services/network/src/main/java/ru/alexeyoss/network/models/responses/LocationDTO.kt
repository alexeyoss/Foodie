package ru.alexeyoss.network.models.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class LocationDTO(
    @SerializedName("city_name")
    @Expose
    val cityName: String
)
