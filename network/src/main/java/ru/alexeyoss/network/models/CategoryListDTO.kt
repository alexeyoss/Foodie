package ru.alexeyoss.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class CategoryListDTO(
    @SerializedName("сategories")
    @Expose
    val categories: List<CategoryDTO>
)