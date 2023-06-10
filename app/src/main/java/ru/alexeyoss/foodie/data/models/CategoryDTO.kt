package ru.alexeyoss.foodie.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryDTO(
    val id: Int,
    val name: String,
    val imageUrl: String
) : Parcelable