package ru.alexeyoss.features.dishes.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.alexeyoss.features.dishes.presentation.dishes.adapters.Item

@Parcelize
data class UiDishDTO(
    val id: Int,
    val name: String,
    val price: Int,
    val weight: Int,
    val description: String,
    val imageUrl: String,
    val tegs: ArrayList<String>,
) : Parcelable, Item