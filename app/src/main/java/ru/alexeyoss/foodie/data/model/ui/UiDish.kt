package ru.alexeyoss.foodie.data.model.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UiDish(
    val id: Int,
    val name: String,
    val price: Int,
    val weight: Int,
    val description: String,
    val imageUrl: String,
    val tegs: ArrayList<String>,
    val isFavorite: Boolean,
    val isInCart: Boolean
) : Parcelable