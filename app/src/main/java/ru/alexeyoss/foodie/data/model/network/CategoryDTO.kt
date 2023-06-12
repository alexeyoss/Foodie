package ru.alexeyoss.foodie.data.model.network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryDTO(
    val id: Int,
    val name: String,
    val imageUrl: String
) : Parcelable