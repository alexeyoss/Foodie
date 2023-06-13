package ru.alexeyoss.foodie.data.model.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class DishDTO(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("price")
    @Expose
    val price: Int,
    @SerializedName("weight")
    @Expose
    val weight: Int,
    @SerializedName("description")
    @Expose
    val description: String,
    @SerializedName("image_url")
    @Expose
    val imageUrl: String,
    @SerializedName("tegs")
    @Expose
    val tegs: ArrayList<String>
)
