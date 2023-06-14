package ru.alexeyoss.foodie.data.model.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class DishesList(
    @SerializedName("dishes")
    @Expose
    val dishes: ArrayList<DishDTO>
)
