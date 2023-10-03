package ru.alexeyoss.foodie.services.network.models.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class CategoryDTO(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("image_url")
    @Expose
    val image_url: String
)