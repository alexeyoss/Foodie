package ru.alexeyoss.foodie.data.model.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.alexeyoss.foodie.data.model.network.DishDTO

@Parcelize
data class UiDish(
    val dish: DishDTO,
    val isFavorite: Boolean = false,
    val isInCart: Boolean = false
) : Parcelable