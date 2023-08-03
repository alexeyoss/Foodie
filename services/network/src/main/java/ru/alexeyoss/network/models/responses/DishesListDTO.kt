package ru.alexeyoss.network.models.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class DishesListDTO(
    @SerializedName("dishes")
    @Expose
    val dishes: ArrayList<DishDTO>
)
