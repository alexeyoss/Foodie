package ru.alexeyoss.network.dishes.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class DishesListDTO(
    @SerializedName("dishes")
    @Expose
    val dishes: ArrayList<DishDTO>
)
