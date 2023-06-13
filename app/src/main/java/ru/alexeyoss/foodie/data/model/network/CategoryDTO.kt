package ru.alexeyoss.foodie.data.model.network

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