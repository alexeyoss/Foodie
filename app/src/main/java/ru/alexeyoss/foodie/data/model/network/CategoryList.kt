package ru.alexeyoss.foodie.data.model.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class CategoryList(
    @SerializedName("сategories")
    @Expose
    val categories: List<CategoryDTO>
)